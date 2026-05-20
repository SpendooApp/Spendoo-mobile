package com.spendoo.categories.presentation.api

import androidx.compose.runtime.Composable
import com.spendoo.categories.api.CategoriesFeatureApi
import com.spendoo.categories.presentation.navigation.CategoriesNavHost
import com.spendoo.categories.presentation.navigation.CategoriesRoute

class CategoriesFeatureApiImpl : CategoriesFeatureApi {

    @Composable
    override fun TabEntry(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        CategoriesNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = CategoriesRoute
        )
    }
}