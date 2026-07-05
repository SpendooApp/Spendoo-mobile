package com.spendoo.statistics.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class StatisticsRoute(
    val userId: String? = null,
    val userName: String? = null,
    val userImageUrl: String? = null
) : NavKey

@Serializable
data class ExportRoute(val targetUserId: String? = null) : NavKey

@Serializable
data object DownloadRoute : NavKey
