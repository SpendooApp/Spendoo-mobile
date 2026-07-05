package com.spendoo.identity.presentation.api

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.spendoo.identity.api.CreateNewPasswordRoute
import com.spendoo.identity.api.EditProfileRoute
import com.spendoo.identity.api.FollowersRoute
import com.spendoo.identity.api.FollowingRoute
import com.spendoo.identity.api.ForgetPasswordRoute
import com.spendoo.identity.api.IdentityFeatureApi
import com.spendoo.identity.api.LoginRoute
import com.spendoo.identity.api.OnBoardingRoute
import com.spendoo.identity.api.ProfileRoute
import com.spendoo.identity.api.SignUpRoute
import com.spendoo.identity.api.SplashRoute
import com.spendoo.identity.api.SubscriptionRoute
import com.spendoo.identity.api.VerifyEmailRoute
import com.spendoo.identity.presentation.screen.createNewPassword.CreateNewPasswordScreen
import com.spendoo.identity.presentation.screen.editProfile.EditProfileScreen
import com.spendoo.identity.presentation.screen.followers.FollowersScreen
import com.spendoo.identity.presentation.screen.following.FollowingScreen
import com.spendoo.identity.presentation.screen.forgetPassword.ForgetPasswordScreen
import com.spendoo.identity.presentation.screen.login.LoginScreen
import com.spendoo.identity.presentation.screen.onboarding.OnboardingScreen
import com.spendoo.identity.presentation.screen.profile.ProfileScreen
import com.spendoo.identity.presentation.screen.signup.SignUpScreen
import com.spendoo.identity.presentation.screen.splash.SplashScreen
import com.spendoo.identity.presentation.screen.subscription.SubscriptionScreen
import com.spendoo.identity.presentation.screen.verifyEmail.VerifyEmailScreen

class IdentityFeatureApiImpl : IdentityFeatureApi {


    override fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<SplashRoute> { SplashScreen() }
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
                ProfileScreen()
            }
            entry<FollowingRoute> {
                FollowingScreen()
            }
            entry<FollowersRoute> {
                FollowersScreen()
            }
            entry<EditProfileRoute> {
                EditProfileScreen()
            }
            entry<SubscriptionRoute> {
                SubscriptionScreen()
            }
        }
    }

}