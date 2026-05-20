package com.spendoo.home.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Stable
interface HomeFeatureApi {
    @Composable
    fun TabEntry(
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit = { _, _, _, _, _, _ -> }
    )
}