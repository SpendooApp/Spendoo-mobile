package com.spendoo.categories.domain.entity.scheduledPayment

data class ScheduledPaymentsSummary(
    val upcomingCount: Int,
    val totalBudget: Double,
    val totalSpent: Double,
    val addedIncome: Double
)
