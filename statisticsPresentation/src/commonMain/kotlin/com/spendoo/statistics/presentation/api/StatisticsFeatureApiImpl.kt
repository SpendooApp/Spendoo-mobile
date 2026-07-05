package com.spendoo.statistics.presentation.api

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.spendoo.statistics.api.StatisticsFeatureApi
import com.spendoo.statistics.api.StatisticsRoute
import com.spendoo.statistics.api.ExportRoute
import com.spendoo.statistics.api.DownloadRoute
import com.spendoo.statistics.presentation.screen.statistics.StatisticsScreen
import com.spendoo.statistics.presentation.screen.export.ExportScreen
import com.spendoo.statistics.presentation.screen.download.DownloadScreen

class StatisticsFeatureApiImpl : StatisticsFeatureApi {
    override fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<StatisticsRoute> { route -> StatisticsScreen(route.userId, route.userName, route.userImageUrl) }
            entry<ExportRoute> { route -> ExportScreen(route.targetUserId) }
            entry<DownloadRoute> { DownloadScreen() }
        }
    }
}
