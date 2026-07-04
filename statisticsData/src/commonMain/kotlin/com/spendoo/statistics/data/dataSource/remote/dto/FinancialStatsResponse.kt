package com.spendoo.statistics.data.dataSource.remote.dto

import com.spendoo.statistics.domain.entity.FinancialStats
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FinancialStatsResponse(
    @SerialName("buckets")
    val buckets: List<StatsBucketDto>,
    @SerialName("highestSpendingBucketIndex")
    val highestSpendingBucketIndex: Int,
    @SerialName("highestValue")
    val highestValue: Double
)

fun FinancialStatsResponse.toDomain(): FinancialStats {
    return FinancialStats(
        buckets = this.buckets.map { it.toDomain() },
        highestSpendingBucketIndex = this.highestSpendingBucketIndex,
        highestValue = this.highestValue
    )
}
