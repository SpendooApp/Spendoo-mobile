package com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment

import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPaymentsSummary
import kotlinx.serialization.Serializable

@Serializable
data class ScheduledPaymentsSummaryDto(
    val upcomingCount: Int,
    val totalScheduledAmount: Double
)

fun ScheduledPaymentsSummaryDto.toDomain(): ScheduledPaymentsSummary {
    return ScheduledPaymentsSummary(
        upcomingCount = this.upcomingCount,
        totalScheduledAmount = this.totalScheduledAmount
    )
}
