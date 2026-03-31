package com.spendoo.categories.presentation.api

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.categories.api.CategoriesFeatureApi

class CategoriesFeatureApiImpl : CategoriesFeatureApi {

    @Composable
    override fun TabEntry(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        Box(
            Modifier.fillMaxSize().background(Color.Yellow),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Column {
                Text("Home", Theme.typography.label.medium.medium)
                Text(
                    "go to categories",
                    Theme.typography.label.medium.medium,
                    modifier = Modifier.clickable {

                    })
            }
        }
    }
}