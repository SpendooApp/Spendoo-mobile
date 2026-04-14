package com.spendoo.categories.presentation.api

import androidx.compose.runtime.Composable
import com.spendoo.categories.api.CategoriesFeatureApi
import com.spendoo.categories.screen.addCategory.AddCategoryScreen

class CategoriesFeatureApiImpl : CategoriesFeatureApi {

    @Composable
    override fun TabEntry(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        AddCategoryScreen()
    }
}