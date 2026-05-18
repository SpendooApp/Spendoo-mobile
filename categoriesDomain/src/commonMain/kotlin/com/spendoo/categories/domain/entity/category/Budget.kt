package com.spendoo.categories.domain.entity.category

data class Budget(
    val amount: Double,
    val spentAmount: Double,
    val spendingPercentage: Int,
    val period: Int,
    val startDate: String,
    val endDate: String,
)