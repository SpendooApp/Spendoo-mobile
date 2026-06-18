package com.spendoo.home.presentation.api

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.spendoo.home.api.HomeFeatureApi
import com.spendoo.home.api.HomeRoute
import com.spendoo.home.presentation.screen.HomeScreen

class HomeFeatureApiImpl : HomeFeatureApi {

    override fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<HomeRoute> { HomeScreen() }
        }
    }
}
