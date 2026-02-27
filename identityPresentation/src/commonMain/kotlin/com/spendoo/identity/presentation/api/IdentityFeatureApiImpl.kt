package com.spendoo.identity.presentation.api

import androidx.compose.runtime.Composable
import com.spendoo.identity.api.IdentityFeatureApi
import com.spendoo.identity.presentation.navigation.IdentityNavHost
import com.spendoo.identity.presentation.navigation.OnBoardingRoute

class IdentityFeatureApiImpl : IdentityFeatureApi {

    @Composable
    override fun TabEntry(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        IdentityNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = OnBoardingRoute
        )
    }

    @Composable
    override fun LoginFlow(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        IdentityNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = OnBoardingRoute
        )
    }

    @Composable
    override fun OnBoardingFlow(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        IdentityNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = OnBoardingRoute
        )
    }
}