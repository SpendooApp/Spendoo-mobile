# Agents rules and best practices

This document serves as the central knowledge base, guidelines, and rules for AI agents and developers working on this project.

## 🏗 Project Architecture & System Design
This is a **Kotlin Multiplatform (KMP)** project targeting Android and iOS, built with **Compose Multiplatform**. It strictly follows **Clean Architecture** and **Feature-Based Modularization (Domain-Driven Design)**.

### Build Approach
The project uses custom **Gradle Convention Plugins** defined in the `build-logic` directory. 
- **Rule**: Do not apply standard plugins manually in individual module `build.gradle.kts` files. Always use the appropriate custom convention plugin (e.g., `[project].kmp.feature.domain`).
- Available convention plugins typically include patterns like: `kmpFeatureApi`, `kmpFeatureDomain`, `kmpFeatureData`, `kmpFeaturePresentation`, `kmpApplication`, `kmpLibrary`, `kmpComposeLibrary`.

### Module Layers & Responsibilities
Each feature is split into specific layers, each with its own responsibilities and dependency constraints:

#### 1. `FeatureApi` (e.g., `[feature]Api`)
- **Responsibility**: Exposes public interfaces, shared models, and navigation entry points that other modules can consume.
- **Dependencies**: Minimal. Typically only Compose Runtime and UI for exposing composable entry points.
- **Rule**: Keep this lightweight. Other features depend on this to navigate without pulling the full feature implementation.

#### 2. `FeatureDomain` (e.g., `[feature]Domain`)
- **Responsibility**: Contains the core business logic, Entities/Models, and Repository Interfaces.
- **Structure**: Grouped into `entity`, `repository`, and `utils`. 
- **Rule**: **No Use Cases**. ViewModels interact directly with Repository Interfaces.
- **Dependencies**: Koin, Coroutines, Kotlinx DateTime.
- **STRICT RULE**: **No UI, No Android frameworks, No Network (Ktor) or Database dependencies**. The domain layer must remain completely isolated from implementation details.

#### 3. `FeatureData` (e.g., `[feature]Data`)
- **Responsibility**: Implements repository interfaces defined in the Domain layer. Manages data sources like Network (API calls) and Local Storage.
- **Dependencies**: Ktor, Kotlinx Serialization, Multiplatform Settings, Koin, Coroutines.
- **Rule**: Data models (DTOs) should be mapped to Domain entities before being returned to the Domain layer.
- **Mocks**: If mock data is needed, it must be implemented here in the `Data` module (e.g., inside the `RepositoryImpl`), **never** inside the ViewModel, and don't do mocks until I explicitly ask for them.
- **BaseGateway**: All network/remote repositories must inherit from `BaseGateway` (typically located in the `shared` package of the data module). The `BaseGateway` provides a `tryToExecute` method that standardizes error handling by catching Ktor exceptions and mapping HTTP status codes to standard Domain Exceptions (like `UnAuthorizedException`, `NoNetworkException`, etc.) before they reach the Domain layer.

#### 4. `FeaturePresentation` (e.g., `[feature]Presentation`)
- **Responsibility**: Contains UI components, Screens, ViewModels, UI State management, and routing.
- **Dependencies**: Compose Multiplatform, Navigation Compose, Coil, Koin, Design System.
- **Navigation Pattern**: 
  - Uses **Jetpack Navigation 3** (`NavDisplay`) for navigation.
  - **Single Flat Backstack**: The entire application shares a single global `NavBackStack<NavKey>` managed in the `NavigationRoot`.
  - **Effector for Navigation**: Navigation actions are dispatched via an `Effector` interface. `BaseViewModel` provides helper functions like `navigate(route)`, `popBackStack()`, and `resetTo(route)` that delegate to the `Effector`.
  - **Feature API Isolation**: Feature API modules expose parameterless composable screens using the Jetpack Navigation 3 `entryProvider { entry<Route> { Screen() } }` pattern. They must have zero dependencies on other modules (e.g., no dependency on `:designSystem`).
  - **Type-Safe Bottom Bar Visibility**: Visibility of the bottom navigation bar is dynamically derived using type-safe Kotlin checks in `EntryPoint.kt` (e.g., `currentRoute is HomeRoute || currentRoute is CategoriesRoute || currentRoute is StatisticsRoute || currentRoute is ChatbotRoute`).
  - **Reactive Results Flow**: Passing data back to previous screens is handled reactively using the `ResultStore` through `BaseViewModel.popBackStack("key" to value)` and `BaseViewModel.getResult<T>("key")`.
  - **No `SavedStateHandle.toRoute<T>()` in Navigation 3**: Since `SavedStateHandle` is not populated with route properties automatically by Navigation 3, do not use `savedStateHandle.toRoute<T>()` to retrieve navigation arguments inside ViewModels. Instead, pass parameters explicitly from the route key in `entryProvider` / `entry` lambdas to the Composable screen, and use Koin's `parametersOf` (e.g., `koinViewModel(parameters = { parametersOf(...) })`) to inject those values directly into the ViewModel's constructor.
- **Screen Structure**: Each screen is strictly broken down into four components to separate concerns and ensure Unidirectional Data Flow:
  1. **`[Screen]Screen.kt`**: The Composable function that observes the UI state and renders the UI. It passes user actions to the `InteractionListener`.
  2. **`[Screen]ViewModel.kt`**: Manages business logic, holds the `UiState`, and handles intents/events via the listener.
     - **Rule**: All ViewModels MUST inherit from `BaseViewModel` (located in `designSystem/navigation/BaseViewModel.kt`). `BaseViewModel` provides:
       - State management via `MutableStateFlow` (`updateState` helper).
       - Centralized navigation and snackbar functions via `Effector` and `SnackBarManager` delegation.
       - Coroutine helpers (`tryToCall`, `tryToCollect`, `createPaginator`) that safely handle exceptions, loading states, pagination and threading automatically.
       - **Coroutine Cancellation**: `tryToCall` and `tryToCollect` automatically ignore `CancellationException` to prevent "Job was canceled" snackbars when the user navigates away. Do not catch or swallow `CancellationException` in repository or View Model code without rethrowing it, to preserve proper coroutine cancellation.
     - **Rule**: ViewModels interact directly with Repositories. Do not use Use Cases. Do not put mock data here.
  3. **`[Screen]UiState.kt`**: A data class representing the immutable state of the screen.
  4. **`[Screen]InteractionListener.kt`**: An interface defining all user interactions/events that the ViewModel implements. The UI composable calls these methods.

#### 5. `DesignSystem` 
- **Responsibility**: The single source of truth for UI consistency. It holds the generic theme (`colors`, `typography`, `theme` configurations) and reusable UI components (`cards`, `buttons`, `appBar`, `textFields`, `bottomNavigation`, `snackbar`, `sheet`, etc.).
- **Rule**: Feature modules must **never** hardcode colors, padding values, or typographies. Always use the predefined styles and components from the `DesignSystem` module to maintain a cohesive look and feel across the app.

## 🛠 Best Practices
1. **Dependency Injection**: Use **Koin** for DI across all modules. Define Koin modules in the `di` package of each layer.
2. **Cross-Module Communication**: Features should communicate via their `Api` modules to avoid circular dependencies and tight coupling.
3. **File Structure**: Every class, enum, or entity must be in its own separate file. Do not group multiple classes or enums into a single file.
4. **Dates in Domain**: Always use `kotlinx.datetime.LocalDate` or `kotlinx.datetime.LocalDateTime` for date/time fields in the Domain layer. Never use `Long` or primitives for dates.
5. **Full Implementation**: Never leave placeholder comments (e.g., `// TODO handle error`) or mock logic in ViewModels or Repositories when a real implementation is expected. Always implement the full flow, including error handling (e.g., calling `showSnackBar`) and endpoint execution.
6. **Mappers**: Mapper functions (like `toDomain()` or `toDto()`) should be placed directly in the same file as the DTO class, rather than creating a separate `mapper` package.
7. **UI Logic & Mappings**: Do not map enums, entities, or complex state to Strings/other UI states directly inside Compose UI files or ViewModels. Instead, create mapping/extension functions inside the `UiState.kt` file (e.g., `fun PaymentFrequency.toText(): StringResource`, or converting between different screen states) and call/use them from the UI or ViewModel.
8. **No Fully Qualified Package Names**: Never write fully qualified package names inline in the code (e.g. `com.spendoo...SomeClass`). Always import the class at the top of the file.
9. **String Localization**: Never use hardcoded/constant strings in the UI, ViewModels, or UI states. All user-facing strings must be localized using resource files (e.g., `Res.string.some_key`). If localized strings are needed in the ViewModel, use `getString` suspend functions or use a `UiText` wrapper.
10. **No Non-null Assertion Operator (`!!`)**: Never use the non-null assertion operator `!!`. Write smart code that handles nullability safely using smart casts, early returns, safe calls (`?.let`), or meaningful exceptions if absolutely critical.
11. **Placeholder Comments**: Any temporary placeholders or incomplete implementations (e.g. placeholder icons, click listeners) must be documented with a comment starting explicitly with `// TODO`.
12. **Build Validation**: Always run a compilation check or build (e.g., using `.\gradlew.bat compileKotlinMetadata` or equivalent build task) to verify that all code compiles successfully without errors before finishing a task.
13. **Data Transfer Objects (DTOs)**: When defining DTOs for network requests, you can use domain Enums directly instead of mapping them to Strings, as `kotlinx.serialization` handles Enums automatically.
14. **Custom Request Timeouts**: For endpoints requiring customized timeouts (e.g., long-running AI operations or media uploads), use the request-level `timeout` configuration block (provided by Ktor's `HttpTimeout` plugin) rather than modifying global client timeout settings.

## 🤖 Dynamic Learning Rule
**IMPORTANT FOR ALL AGENTS**:
If you learn something from the user during a conversation, and it is a general best practice or rule that can be applied later for new edits (not just a one-off specific case), you MUST update this `Agents.md` file to add it as a rule. This ensures continuous learning and adaptation to the user's coding style and preferences.

