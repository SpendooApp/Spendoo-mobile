package com.spendoo.goals.presentation.api

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
import com.spendoo.goals.api.AchievementsRoute
import com.spendoo.goals.api.GoalsFeatureApi
import com.spendoo.goals.api.GoalsRoute
import com.spendoo.goals.presentation.screen.goals.GoalsScreen

class GoalsFeatureApiImpl : GoalsFeatureApi {

    override operator fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<GoalsRoute> { GoalsScreen() }
            entry<AchievementsRoute> {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            "Achievements",
                            style = Theme.typography.label.medium.medium,
                            color = Theme.colorScheme.text.title
                        )
                    }
                }
            }
        }
    }
}
