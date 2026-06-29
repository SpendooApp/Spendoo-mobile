package com.spendoo.statistics.domain.entity

data class FinancialStats(
    val buckets: List<StatsBucket>,
    val highestSpendingBucketIndex: Int,
    val highestValue: Double
)
