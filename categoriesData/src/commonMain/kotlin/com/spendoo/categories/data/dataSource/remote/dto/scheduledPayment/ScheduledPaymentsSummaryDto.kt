package com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment

import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPaymentsSummary
import kotlinx.serialization.Serializable

@Serializable
data class ScheduledPaymentsSummaryDto(
    val upcomingCount: Int,
    val totalBudget: Double,
    val totalSpent: Double,
    val addedIncome: Double
)

fun ScheduledPaymentsSummaryDto.toDomain(): ScheduledPaymentsSummary {
    return ScheduledPaymentsSummary(
        upcomingCount = this.upcomingCount,
        totalBudget = this.totalBudget,
        totalSpent = this.totalSpent,
        addedIncome = this.addedIncome
    )
}
