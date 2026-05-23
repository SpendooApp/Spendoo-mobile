package com.spendoo.home.presentation.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.spendoo.home.api.HomeFeatureApi
import com.spendoo.home.presentation.navigation.HomeNavHost
import com.spendoo.home.presentation.navigation.HomeRoute

class HomeFeatureApiImpl : HomeFeatureApi {

    @Composable
    override fun TabEntry(
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit,
        reloadSignal: Long,
        shouldReload: Boolean
    ) {
        HomeNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = HomeRoute,
            showSnackBar = showSnackBar,
            reloadSignal = reloadSignal,
            shouldReload = shouldReload
        )
    }
}
