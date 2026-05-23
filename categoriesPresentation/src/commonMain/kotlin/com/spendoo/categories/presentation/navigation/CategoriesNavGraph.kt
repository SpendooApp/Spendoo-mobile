package com.spendoo.categories.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionBottomSheet
import com.spendoo.categories.presentation.screen.categories.CategoriesScreen

@Composable
fun CategoriesNavGraph(
    navController: NavHostController,
    startDestination: BaseRoute,
    reloadSignal: Long,
    shouldReload: Boolean,
    isAddTransactionBottomSheetVisible: Boolean,
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
    ) {
        composable<CategoriesRoute> {
            CategoriesScreen(
                reloadSignal = reloadSignal,
                shouldReload = shouldReload
            )
        }

        composable<AddTransactionRoute> {
            AddTransactionBottomSheet(
                isVisible = isAddTransactionBottomSheetVisible
            )
        }
    }
}
