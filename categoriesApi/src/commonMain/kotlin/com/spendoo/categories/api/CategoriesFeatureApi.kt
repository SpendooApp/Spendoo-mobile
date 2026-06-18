package com.spendoo.categories.api

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

@Stable
interface CategoriesFeatureApi {
    operator fun invoke(): (NavKey) -> NavEntry<NavKey>
}