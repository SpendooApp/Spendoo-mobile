package com.spendoo.categories.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface CategoriesFeatureApi {
    @Composable
    fun TabEntry(updateBottomNavigationVisibility: (Boolean) -> Unit)
}