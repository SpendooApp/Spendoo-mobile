package com.spendoo.identity.presentation.navigation

import kotlinx.serialization.Serializable

interface BaseRoute

@Serializable
data object OnBoardingRoute : BaseRoute

@Serializable
data object HomeRoute : BaseRoute

@Serializable
data object LoginRoute : BaseRoute

@Serializable
data object SignUpRoute : BaseRoute

@Serializable
data object ForgetPasswordRoute : BaseRoute


@Serializable
data class VerifyEmailRoute(val email: String, val isForgetPasswordFlow: Boolean) : BaseRoute

@Serializable
data class CreateNewPasswordRoute(val email: String, val otp: String) : BaseRoute

@Serializable
data object ProfileRoute : BaseRoute
