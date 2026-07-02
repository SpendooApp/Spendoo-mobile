package com.spendoo.identity.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute : NavKey

@Serializable
data object OnBoardingRoute : NavKey

@Serializable
data object LoginRoute : NavKey

@Serializable
data object SignUpRoute : NavKey

@Serializable
data object ForgetPasswordRoute : NavKey

@Serializable
data class VerifyEmailRoute(val email: String, val isForgetPasswordFlow: Boolean) : NavKey

@Serializable
data class CreateNewPasswordRoute(val email: String, val otp: String) : NavKey

@Serializable
data object ProfileRoute : NavKey

@Serializable
data object SubscriptionRoute : NavKey