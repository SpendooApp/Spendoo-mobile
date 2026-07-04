package com.spendoo.statistics.domain.entity

import kotlinx.datetime.LocalDate

data class ExportChoices(
    val timePeriod: TimePeriod,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val reportType: ReportType,
    val dataToInclude: DataToInclude?
)
