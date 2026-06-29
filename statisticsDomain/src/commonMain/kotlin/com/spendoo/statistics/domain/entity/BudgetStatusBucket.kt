package com.spendoo.statistics.domain.entity

import kotlinx.datetime.LocalDateTime

data class BudgetStatusBucket(
    val spending: Double,
    val status: BudgetStatus,
    val percentage: Double,
    val startDate: LocalDateTime
)
