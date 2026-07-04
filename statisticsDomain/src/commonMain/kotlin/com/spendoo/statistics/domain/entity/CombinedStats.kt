package com.spendoo.statistics.domain.entity

data class CombinedStats(
    val financialStats: FinancialStats,
    val budgetStatus: BudgetStatusInfo,
    val topCategories: TopCategories
)
