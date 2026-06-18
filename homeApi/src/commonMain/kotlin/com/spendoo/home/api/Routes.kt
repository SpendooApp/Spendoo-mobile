package com.spendoo.home.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavKey

@Serializable
data object ChatBotRoute : NavKey //TODO move it to the right place