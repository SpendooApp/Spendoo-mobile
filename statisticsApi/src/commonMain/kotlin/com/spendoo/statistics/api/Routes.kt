package com.spendoo.statistics.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object StatisticsRoute : NavKey

@Serializable
data object ExportRoute : NavKey

@Serializable
data object DownloadRoute : NavKey
