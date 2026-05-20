package com.spendoo.home.presentation.navigation

import kotlinx.serialization.Serializable

interface BaseRoute

@Serializable
data object HomeRoute : BaseRoute
