package com.spendoo.identity.presentation.api

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.api.CreateNewPasswordRoute
import com.spendoo.identity.api.ForgetPasswordRoute
import com.spendoo.identity.api.IdentityFeatureApi
import com.spendoo.identity.api.LoginRoute
import com.spendoo.identity.api.OnBoardingRoute
import com.spendoo.identity.api.ProfileRoute
import com.spendoo.identity.api.SignUpRoute
import com.spendoo.identity.api.SplashRoute
import com.spendoo.identity.api.VerifyEmailRoute
import com.spendoo.identity.presentation.screen.createNewPassword.CreateNewPasswordScreen
import com.spendoo.identity.presentation.screen.forgetPassword.ForgetPasswordScreen
import com.spendoo.identity.presentation.screen.login.LoginScreen
import com.spendoo.identity.presentation.screen.onboarding.OnboardingScreen
import com.spendoo.identity.presentation.screen.signup.SignUpScreen
import com.spendoo.identity.presentation.screen.verifyEmail.VerifyEmailScreen

class IdentityFeatureApiImpl : IdentityFeatureApi {

    override fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<SplashRoute> {
                Box(Modifier.fillMaxSize().background(Theme.colorScheme.background.primary))
            }
            entry<OnBoardingRoute> { OnboardingScreen() }
            entry<LoginRoute> { LoginScreen() }
            entry<SignUpRoute> { SignUpScreen() }
            entry<ForgetPasswordRoute> { ForgetPasswordScreen() }
            entry<VerifyEmailRoute> { route ->
                VerifyEmailScreen(
                    email = route.email,
                    isForgetPasswordFlow = route.isForgetPasswordFlow
                )
            }
            entry<CreateNewPasswordRoute> { route ->
                CreateNewPasswordScreen(
                    email = route.email,
                    otp = route.otp
                )
            }
            entry<ProfileRoute> {
                Box(
                    Modifier.fillMaxSize().background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text("Profile", Theme.typography.label.medium.medium)
                    }
                }
            }
        }
    }
}