package com.spendoo.home.presentation.api

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.home.api.HomeFeatureApi
import com.spendoo.home.api.HomeRoute
import com.spendoo.home.api.NotificationsRoute
import com.spendoo.home.presentation.screen.HomeScreen
import com.spendoo.home.presentation.screen.notifications.NotificationsScreen

class HomeFeatureApiImpl : HomeFeatureApi {

    override fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<HomeRoute> { HomeScreen() }
            entry<NotificationsRoute> { NotificationsScreen() }
        }
    }
}
