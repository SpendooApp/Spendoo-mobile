package com.spendoo.appEntryPoint

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.rememberNavBackStack
import com.spendoo.categories.api.AddTransactionRoute
import com.spendoo.categories.api.CategoriesRoute
import com.spendoo.designsystem.components.snackbar.AnimatedSnackBar
import com.spendoo.designsystem.navigation.effector.Effect
import com.spendoo.designsystem.navigation.effector.EffectHandler
import com.spendoo.designsystem.navigation.effector.Effector
import com.spendoo.home.api.HomeRoute
import com.spendoo.identity.api.CreateNewPasswordRoute
import com.spendoo.identity.api.ForgetPasswordRoute
import com.spendoo.identity.api.LoginRoute
import com.spendoo.identity.api.OnBoardingRoute
import com.spendoo.identity.api.SignUpRoute
import com.spendoo.identity.api.SplashRoute
import com.spendoo.identity.api.VerifyEmailRoute
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.service.AuthorizationService
import com.spendoo.navigation.AppBottomNavigationBar
import com.spendoo.navigation.NavigationRoot
import com.spendoo.statistics.api.StatisticsRoute
import com.spendoo.util.buildNavigationSerializerConfig
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EntryPoint(
    viewModel: MainEntryViewModel = koinViewModel(),
    authorizationService: AuthorizationService = koinInject(),
    settingsRepository: SettingsRepository = koinInject(),
    effector: Effector = koinInject(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val accessToken by authorizationService.observeAccessToken().collectAsStateWithLifecycle()
    val isOnBoardingCompleted by settingsRepository.observeOnBoardingCompleted()
        .collectAsStateWithLifecycle()

    val navigationSerializerConfig = buildNavigationSerializerConfig()

    val backStack = rememberNavBackStack(navigationSerializerConfig, SplashRoute)
    val currentRoute = backStack.lastOrNull()

    EffectHandler(effector.effect) { effect ->
        when (effect) {
            is Effect.Navigate -> {
                if (effect.route != currentRoute) backStack.add(effect.route)
            }

            is Effect.PopBackStack -> {
                backStack.removeLastOrNull()
            }

            is Effect.ResetTo -> {
                backStack.clear()
                backStack.add(effect.route)
            }
        }
    }


    val showBottomNavigation = currentRoute is HomeRoute
            || currentRoute is CategoriesRoute
            || currentRoute is StatisticsRoute
            || currentRoute is AddTransactionRoute

    val activeFeature = currentRoute

    LaunchedEffect(isOnBoardingCompleted, accessToken) {
        val targetRoute = when {
            !isOnBoardingCompleted -> OnBoardingRoute
            accessToken.isBlank() -> LoginRoute
            else -> HomeRoute
        }

        val isUnauthRoute = currentRoute == SplashRoute 
                || currentRoute == OnBoardingRoute 
                || currentRoute == LoginRoute 
                || currentRoute == SignUpRoute 
                || currentRoute == ForgetPasswordRoute 
                || currentRoute == VerifyEmailRoute 
                || currentRoute == CreateNewPasswordRoute

        if (targetRoute == HomeRoute) {
            if (isUnauthRoute) {
                effector.resetTo(targetRoute, true)
            }
        } else {
            if (currentRoute != targetRoute) {
                effector.resetTo(targetRoute, true)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedSnackBar(
            isVisible = state.isSnackBarVisible,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 16.dp, start = 12.dp, end = 16.dp),
            onDismiss = viewModel::hideSnackBar,
            data = state.snackBarData
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavigationRoot(backStack)

            AppBottomNavigationBar(
                showBottomNavigation,
                activeFeature,
                viewModel
            )
        }
    }
}