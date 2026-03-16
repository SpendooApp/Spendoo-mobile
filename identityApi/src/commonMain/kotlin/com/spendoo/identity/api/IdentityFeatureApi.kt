package com.spendoo.identity.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
interface IdentityFeatureApi {
    @Composable
    fun TabEntry(updateBottomNavigationVisibility: (Boolean) -> Unit)

    @Composable
    fun LoginFlow(updateBottomNavigationVisibility: (Boolean) -> Unit)

    @Composable
    fun OnBoardingFlow(updateBottomNavigationVisibility: (Boolean) -> Unit)
}