package com.spendoo.identity.presentation.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.spendoo.identity.api.IdentityFeatureApi
import com.spendoo.identity.presentation.navigation.IdentityNavHost
import com.spendoo.identity.presentation.navigation.LoginRoute
import com.spendoo.identity.presentation.navigation.OnBoardingRoute
import com.spendoo.identity.presentation.navigation.ProfileRoute

class IdentityFeatureApiImpl : IdentityFeatureApi {

    @Composable
    override fun TabEntry(
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit
    ) {
        IdentityNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            showSnackBar = showSnackBar,
            startDestination = ProfileRoute
        )
    }

    @Composable
    override fun LoginFlow(
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit
    ) {
        IdentityNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            showSnackBar = showSnackBar,
            startDestination = LoginRoute
        )
    }

    @Composable
    override fun OnBoardingFlow(
        updateBottomNavigationVisibility: (Boolean) -> Unit,
        showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit
    ) {
        IdentityNavHost(
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            showSnackBar = showSnackBar,
            startDestination = OnBoardingRoute
        )
    }
}