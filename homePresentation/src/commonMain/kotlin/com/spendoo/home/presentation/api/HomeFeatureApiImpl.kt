package com.spendoo.home.presentation.api

import androidx.compose.runtime.Composable
import com.spendoo.home.api.HomeFeatureApi
import com.spendoo.home.presentation.navigation.HomeNavHost
import com.spendoo.home.presentation.navigation.HomeRoute

class HomeFeatureApiImpl : HomeFeatureApi {

    @Composable
    override fun TabEntry(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        HomeNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = HomeRoute
        )
    }
}
