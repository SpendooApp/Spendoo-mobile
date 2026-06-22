package com.spendoo.statistics.data.dataSource.remote.dto

import com.spendoo.statistics.domain.entity.BudgetStatusInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetStatusResponse(
    @SerialName("buckets")
    val buckets: List<BudgetStatusBucketDto>,
    @SerialName("highestSpending")
    val highestSpending: Double
)

fun BudgetStatusResponse.toDomain(): BudgetStatusInfo {
    return BudgetStatusInfo(
        buckets = this.buckets.map { it.toDomain() },
        highestSpending = this.highestSpending
    )
}
