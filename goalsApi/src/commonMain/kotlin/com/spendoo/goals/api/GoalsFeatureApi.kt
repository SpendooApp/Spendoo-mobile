package com.spendoo.goals.api

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

@Stable
interface GoalsFeatureApi {
    operator fun invoke(): (NavKey) -> NavEntry<NavKey>
}
