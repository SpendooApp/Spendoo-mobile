package com.spendoo.identity.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.home.api.HomeFeatureApi
import com.spendoo.identity.presentation.screen.forgetPassword.ForgetPasswordScreen
import com.spendoo.identity.presentation.screen.login.LoginScreen
import com.spendoo.identity.presentation.screen.verifyEmail.VerifyEmailScreen
import com.spendoo.identity.presentation.screen.onboarding.OnboardingScreen
import com.spendoo.identity.presentation.screen.createNewPassword.CreateNewPasswordScreen
import com.spendoo.identity.presentation.screen.signup.SignUpScreen
import org.koin.compose.koinInject

@Composable
fun IdentityNavGraph(
    navController: NavHostController,
    startDestination: BaseRoute,
    updateBottomNavigationVisibility: (Boolean) -> Unit,
    showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit = { _, _, _, _, _, _ -> },
    homeFeatureApi: HomeFeatureApi = koinInject(),
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
        composable<OnBoardingRoute> { OnboardingScreen() }
        composable<LoginRoute> { LoginScreen() }
        composable<SignUpRoute> { SignUpScreen() }
        composable<ForgetPasswordRoute> { ForgetPasswordScreen() }
        composable<VerifyEmailRoute> { VerifyEmailScreen() }
        composable<CreateNewPasswordRoute> { CreateNewPasswordScreen() }
        composable<ProfileRoute> {
            Box(
                Modifier.fillMaxSize().background(Color.Blue),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Column {
                    Text("Profile", Theme.typography.label.medium.medium)
                }
            }
        }
        composable<HomeRoute> {
            homeFeatureApi.TabEntry(
                updateBottomNavigationVisibility = updateBottomNavigationVisibility,
                showSnackBar = showSnackBar
            )
        }
    }
}