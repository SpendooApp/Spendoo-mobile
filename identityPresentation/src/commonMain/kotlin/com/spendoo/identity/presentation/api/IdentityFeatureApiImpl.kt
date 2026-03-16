package com.spendoo.identity.presentation.api

import androidx.compose.runtime.Composable
import com.spendoo.identity.api.IdentityFeatureApi
import com.spendoo.identity.presentation.navigation.IdentityNavHost
import com.spendoo.identity.presentation.navigation.LoginRoute
import com.spendoo.identity.presentation.navigation.OnBoardingRoute
import com.spendoo.identity.presentation.navigation.ProfileRoute

class IdentityFeatureApiImpl : IdentityFeatureApi {

    @Composable
    override fun TabEntry(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        IdentityNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = ProfileRoute
        )
    }

    @Composable
    override fun LoginFlow(updateBottomNavigationVisibility: (Boolean) -> Unit) {
        IdentityNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            startDestination = LoginRoute
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