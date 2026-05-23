package com.spendoo.categories.presentation.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.spendoo.categories.api.CategoriesFeatureApi
import com.spendoo.categories.presentation.navigation.AddTransactionRoute
import com.spendoo.categories.presentation.navigation.CategoriesNavHost
import com.spendoo.categories.presentation.navigation.CategoriesRoute

class CategoriesFeatureApiImpl : CategoriesFeatureApi {

    @Composable
    override fun TabEntry(
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit,
        reloadSignal: Long,
        shouldReload: Boolean,
    ) {
        CategoriesNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = CategoriesRoute,
            reloadSignal = reloadSignal,
            shouldReload = shouldReload,
            showSnackBar = showSnackBar
        )
    }

    @Composable
    override fun AddTransactionBottomSheet(
        isVisible: Boolean,
        onDismiss: () -> Unit,
        onTransactionAdded: () -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit,
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        reloadSignal: Long,
        shouldReload: Boolean,
    ) {
        CategoriesNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = AddTransactionRoute,
            reloadSignal = reloadSignal,
            shouldReload = shouldReload,
            showSnackBar = showSnackBar,
            isAddTransactionBottomSheetVisible = isVisible,
            onDismiss = onDismiss,
            onTransactionAdded = onTransactionAdded
        )
    }
}