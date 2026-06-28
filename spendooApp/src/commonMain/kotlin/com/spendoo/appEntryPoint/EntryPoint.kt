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
import com.spendoo.chatbot.api.ChatbotRoute
import com.spendoo.home.api.HomeRoute
import com.spendoo.identity.api.LoginRoute
import com.spendoo.identity.api.OnBoardingRoute
import com.spendoo.identity.api.SplashRoute
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.service.AuthorizationService
import com.spendoo.navigation.AppBottomNavigationBar
import com.spendoo.navigation.NavigationRoot
import com.spendoo.statistics.api.StatisticsRoute
import com.spendoo.identity.api.SignUpRoute
import com.spendoo.identity.api.ForgetPasswordRoute
import com.spendoo.identity.api.VerifyEmailRoute
import com.spendoo.identity.api.CreateNewPasswordRoute
import com.spendoo.identity.api.ProfileRoute
import com.spendoo.categories.api.ScheduledPaymentsRoute
import com.spendoo.categories.api.ScheduledPaymentDetailsRoute
import com.spendoo.categories.api.TransactionDetailsRoute
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import com.spendoo.goals.api.GoalsRoute
import com.spendoo.statistics.api.DownloadRoute
import com.spendoo.statistics.api.ExportRoute
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

    val navigationSerializerConfig = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(SplashRoute::class, SplashRoute.serializer())
                subclass(OnBoardingRoute::class, OnBoardingRoute.serializer())
                subclass(LoginRoute::class, LoginRoute.serializer())
                subclass(SignUpRoute::class, SignUpRoute.serializer())
                subclass(ForgetPasswordRoute::class, ForgetPasswordRoute.serializer())
                subclass(VerifyEmailRoute::class, VerifyEmailRoute.serializer())
                subclass(CreateNewPasswordRoute::class, CreateNewPasswordRoute.serializer())
                subclass(ProfileRoute::class, ProfileRoute.serializer())
                subclass(HomeRoute::class, HomeRoute.serializer())
                subclass(GoalsRoute::class, GoalsRoute.serializer())
                subclass(ChatbotRoute::class, ChatbotRoute.serializer())
                subclass(CategoriesRoute::class, CategoriesRoute.serializer())
                subclass(AddTransactionRoute::class, AddTransactionRoute.serializer())
                subclass(StatisticsRoute::class, StatisticsRoute.serializer())
                subclass(ScheduledPaymentsRoute::class, ScheduledPaymentsRoute.serializer())
                subclass(ScheduledPaymentDetailsRoute::class, ScheduledPaymentDetailsRoute.serializer())
                subclass(TransactionDetailsRoute::class, TransactionDetailsRoute.serializer())
                subclass(ExportRoute::class, ExportRoute.serializer())
                subclass(DownloadRoute::class, DownloadRoute.serializer())
            }
        }
    }

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
            || currentRoute is ChatbotRoute
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