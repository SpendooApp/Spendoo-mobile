package com.spendoo.statistics.domain.entity

import kotlinx.datetime.LocalDateTime

data class StatsBucket(
    val spending: Double,
    val income: Double,
    val budget: Double,
    val startDate: LocalDateTime,
    val isPredicted: Boolean
)
