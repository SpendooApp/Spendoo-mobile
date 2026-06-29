package com.spendoo.statistics.domain.entity

data class BudgetStatusInfo(
    val buckets: List<BudgetStatusBucket>,
    val highestSpending: Double
)
