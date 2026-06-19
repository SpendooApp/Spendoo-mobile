package com.spendoo.statistics.api

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

@Stable
interface StatisticsFeatureApi {
    operator fun invoke(): (NavKey) -> NavEntry<NavKey>
}
