package com.spendoo.categories.domain.entity.category

import kotlinx.datetime.LocalDateTime

data class Budget(
    val amount: Double,
    val spentAmount: Double,
    val spendingPercentage: Int,
    val period: ResetCycleOption,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)