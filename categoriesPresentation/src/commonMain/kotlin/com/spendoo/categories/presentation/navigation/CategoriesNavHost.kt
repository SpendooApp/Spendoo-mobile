package com.spendoo.categories.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.compose.rememberNavController
import com.spendoo.categories.presentation.navigation.effector.Effect
import com.spendoo.categories.presentation.navigation.effector.EffectHandler
import com.spendoo.categories.presentation.navigation.effector.Effector
import com.spendoo.designsystem.utils.asStringSuspend
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CategoriesNavHost(
    updateBottomNavigationVisibility: (Boolean) -> Unit,
    effector: Effector = koinInject(),
    startDestination: BaseRoute = CategoriesRoute,
    showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit,
    reloadSignal: Long = 0L,
    shouldReload: Boolean = false,
    isAddTransactionBottomSheetVisible: Boolean = true,
    onDismiss: () -> Unit = {},
    onTransactionAdded: (() -> Unit)? = null
) {
    val navController = rememberNavController()

    EffectHandler(effector.effect) { effect ->
        when (effect) {
            is Effect.Navigate -> navController.navigate(
                route = effect.route, navOptions = effect.navOptions
            )

            is Effect.PopBackStack -> {
                val isTransactionAdded = effect.arguments.containsKey(ARG_TRANSACTION_ADDED)

                effect.arguments
                    .filterKeys { key -> key != ARG_TRANSACTION_ADDED }
                    .forEach { (key, value) ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(key, value)
                    }

                if (onTransactionAdded != null) {
                    if (isTransactionAdded) {
                        onTransactionAdded()
                    }
                    if (!navController.popBackStack()) {
                        onDismiss()
                    }
                } else if (!isTransactionAdded && navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
            }

            is Effect.SetBackStackArgs -> {
                effect.arguments.forEach { (key, value) ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(key, value)
                }
            }

            is Effect.PopUpTo -> {
                navController.popBackStack(
                    route = effect.route,
                    inclusive = effect.inclusive,
                    saveState = effect.saveState
                )
            }

            is Effect.UpdateBottomNavigationVisibility -> {
                updateBottomNavigationVisibility(effect.isVisible)
            }

            is Effect.ShowSnackBar -> showSnackBar(
                effect.title.asStringSuspend(),
                effect.message.asStringSuspend(),
                effect.isSuccess,
                effect.customLeadingIcon,
                effect.duration,
                effect.iconTint
            )
        }
    }


    LaunchedEffect(Unit) {
        navController.currentBackStack.collectLatest {
            val route = navController.currentDestination?.route
            // ignore bottom navigation visibility for AddTransactionRoute and null routes
            if (route !in listOf(AddTransactionRoute::class.qualifiedName, null)) {
                if (route in routesWithBottomNavigation) {
                    updateBottomNavigationVisibility(true)
                } else {
                    updateBottomNavigationVisibility(false)
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CategoriesNavGraph(
            navController = navController,
            startDestination = startDestination,
            reloadSignal = reloadSignal,
            shouldReload = shouldReload,
            isAddTransactionBottomSheetVisible = isAddTransactionBottomSheetVisible
        )
    }
}


private val routesWithBottomNavigation = listOf(
    CategoriesRoute::class.qualifiedName
)

const val ARG_TRANSACTION_ADDED = "transaction_added"
