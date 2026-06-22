package com.spendoo.statistics.data.dataSource.remote.dto

import com.spendoo.shared.domain.utils.toLocalDateTimeOrDefault
import com.spendoo.statistics.domain.entity.BudgetStatus
import com.spendoo.statistics.domain.entity.BudgetStatusBucket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetStatusBucketDto(
    @SerialName("spending")
    val spending: Double,
    @SerialName("status")
    val status: BudgetStatus,
    @SerialName("percentage")
    val percentage: Double,
    @SerialName("startDate")
    val startDate: String
)

fun BudgetStatusBucketDto.toDomain(): BudgetStatusBucket {
    return BudgetStatusBucket(
        spending = this.spending,
        status = this.status,
        percentage = this.percentage,
        startDate = this.startDate.toLocalDateTimeOrDefault()
    )
}
