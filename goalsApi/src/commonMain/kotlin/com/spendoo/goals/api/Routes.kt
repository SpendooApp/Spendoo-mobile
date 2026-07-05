package com.spendoo.goals.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object GoalsRoute : NavKey

@Serializable
data object AchievementsRoute : NavKey