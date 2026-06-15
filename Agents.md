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
- **Responsibility**: Contains the core business logic, Entities/Models, Use Cases, and Repository Interfaces.
- **Structure**: Grouped into `entity`, `repository`, `utils`, and use cases.
- **Dependencies**: Koin, Coroutines, Kotlinx DateTime.
- **STRICT RULE**: **No UI, No Android frameworks, No Network (Ktor) or Database dependencies**. The domain layer must remain completely isolated from implementation details.

#### 3. `FeatureData` (e.g., `[feature]Data`)
- **Responsibility**: Implements repository interfaces defined in the Domain layer. Manages data sources like Network (API calls) and Local Storage.
- **Dependencies**: Ktor, Kotlinx Serialization, Multiplatform Settings, Koin, Coroutines.
- **Rule**: Data models (DTOs) should be mapped to Domain entities before being returned to the Domain layer.
- **BaseGateway**: All network/remote repositories must inherit from `BaseGateway` (typically located in the `shared` package of the data module). The `BaseGateway` provides a `tryToExecute` method that standardizes error handling by catching Ktor exceptions and mapping HTTP status codes to standard Domain Exceptions (like `UnAuthorizedException`, `NoNetworkException`, etc.) before they reach the Domain layer.

#### 4. `FeaturePresentation` (e.g., `[feature]Presentation`)
- **Responsibility**: Contains UI components, Screens, ViewModels, UI State management, and routing.
- **Dependencies**: Compose Multiplatform, Navigation Compose, Coil, Koin, Design System.
- **Navigation Pattern**: 
  - Uses Jetpack Navigation Compose (`NavHost`, `NavGraph`). 
  - Uses an **`Effector`** pattern to decouple side-effects (like navigation, snackbars) from the UI components. The `NavHost` observes `Effect`s (e.g., `Effect.Navigate`, `Effect.PopBackStack`, `Effect.ShowSnackBar`) and performs the actual routing or action.
- **Screen Structure**: Each screen is strictly broken down into four components to separate concerns and ensure Unidirectional Data Flow:
  1. **`[Screen]Screen.kt`**: The Composable function that observes the UI state and renders the UI. It passes user actions to the `InteractionListener`.
  2. **`[Screen]ViewModel.kt`**: Manages business logic, holds the `UiState`, handles intents/events via the listener, and triggers `Effects` (e.g., for navigation).
     - **Rule**: All ViewModels MUST inherit from `BaseViewModel` (typically found in the `shared` package of the presentation module). `BaseViewModel` provides:
       - State management via `MutableStateFlow` (`updateState` helper).
       - Effector injection for centralized navigation and side-effects (`navigate`, `popBackStack`, `showSnackBar`, etc.).
       - Coroutine helpers (`tryToCall`, `tryToCollect`) that safely handle exceptions, loading states, and threading automatically.
  3. **`[Screen]UiState.kt`**: A data class representing the immutable state of the screen.
  4. **`[Screen]InteractionListener.kt`**: An interface defining all user interactions/events that the ViewModel implements. The UI composable calls these methods.

#### 5. `DesignSystem` 
- **Responsibility**: The single source of truth for UI consistency. It holds the generic theme (`colors`, `typography`, `theme` configurations) and reusable UI components (`cards`, `buttons`, `appBar`, `textFields`, `bottomNavigation`, `snackbar`, `sheet`, etc.).
- **Rule**: Feature modules must **never** hardcode colors, padding values, or typographies. Always use the predefined styles and components from the `DesignSystem` module to maintain a cohesive look and feel across the app.

## 🛠 Best Practices
1. **Dependency Injection**: Use **Koin** for DI across all modules. Define Koin modules in the `di` package of each layer.
2. **Cross-Module Communication**: Features should communicate via their `Api` modules to avoid circular dependencies and tight coupling.

## 🤖 Dynamic Learning Rule
**IMPORTANT FOR ALL AGENTS**:
If you learn something from the user during a conversation, and it is a general best practice or rule that can be applied later for new edits (not just a one-off specific case), you MUST update this `Agents.md` file to add it as a rule. This ensures continuous learning and adaptation to the user's coding style and preferences.
