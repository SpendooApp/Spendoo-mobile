package com.spendoo.statistics.domain.repository

import com.spendoo.statistics.domain.entity.CombinedStats
import com.spendoo.statistics.domain.entity.Granularity

import kotlinx.datetime.LocalDateTime

interface StatisticsRepository {
    suspend fun getStatistics(
        granularity: Granularity,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): CombinedStats
}
