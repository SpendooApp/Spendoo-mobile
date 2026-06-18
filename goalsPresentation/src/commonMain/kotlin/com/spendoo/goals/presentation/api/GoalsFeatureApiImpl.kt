package com.spendoo.goals.presentation.api

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.spendoo.goals.api.GoalsFeatureApi
import com.spendoo.goals.api.GoalsRoute
import com.spendoo.goals.presentation.screen.goals.GoalsScreen

class GoalsFeatureApiImpl : GoalsFeatureApi {

    override operator fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<GoalsRoute> { GoalsScreen() }
        }
    }
}
