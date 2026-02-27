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
