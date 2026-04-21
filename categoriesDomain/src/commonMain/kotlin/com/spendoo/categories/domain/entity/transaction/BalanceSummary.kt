package com.spendoo.categories.domain.entity.transaction

data class BalanceSummary(
    val totalBalance: Double,
    val income: Double,
    val expenses: Double,
)