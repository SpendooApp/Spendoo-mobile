package com.spendoo.categories.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Stable
interface CategoriesFeatureApi {
    @Composable
    fun TabEntry(
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit,
        reloadSignal: Long = 0L,
        shouldReload: Boolean = false
    )

    @Composable
    fun AddTransactionBottomSheet(
        isVisible: Boolean,
        onDismiss: () -> Unit,
        onTransactionAdded: () -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit,
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        reloadSignal: Long,
        shouldReload: Boolean
    )
}