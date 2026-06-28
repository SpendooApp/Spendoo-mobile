package com.spendoo.statistics.domain.repository

import com.spendoo.statistics.domain.entity.CombinedStats
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.entity.ExportChoices
import com.spendoo.statistics.domain.entity.ReportDataType
import com.spendoo.statistics.domain.entity.Theme
import com.spendoo.statistics.domain.entity.Language
import kotlinx.datetime.LocalDateTime

interface StatisticsRepository {
    suspend fun getStatistics(
        granularity: Granularity,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): CombinedStats

    fun saveExportChoices(choices: ExportChoices)
    fun getExportChoices(): ExportChoices?
    fun clearExportChoices()

    suspend fun getStatisticsPdf(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        reportDataType: ReportDataType,
        theme: Theme,
        lang: Language
    ): ByteArray
}
