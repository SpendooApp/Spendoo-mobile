package com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment

import com.spendoo.categories.domain.entity.scheduledPayment.CreateScheduledPayment
import kotlinx.serialization.Serializable

@Serializable
data class CreateScheduledPaymentDto(
    val title: String,
    val amount: Double,
    val categoryId: String,
    val startDate: String,
    val frequency: String,
    val reminderPeriodValue: Int,
    val reminderPeriodUnit: String
)

fun CreateScheduledPayment.toDto(): CreateScheduledPaymentDto {
    return CreateScheduledPaymentDto(
        title = this.title,
        amount = this.amount,
        categoryId = this.categoryId,
        startDate = this.startDate.toString(),
        frequency = this.frequency.name,
        reminderPeriodValue = this.reminderPeriodValue,
        reminderPeriodUnit = this.reminderPeriodUnit.name
    )
}
