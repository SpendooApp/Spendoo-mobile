package com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment

import com.spendoo.categories.domain.entity.scheduledPayment.CreateScheduledPayment
import kotlinx.serialization.Serializable
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.shared.domain.utils.toLocalDateTime
import com.spendoo.shared.domain.utils.toUtcInstant

@Serializable
data class CreateScheduledPaymentDto(
    val title: String,
    val amount: Double,
    val categoryId: String,
    val startDate: String,
    val frequency: Int,
    val reminderPeriod: Int,
    val reminderUnit: ReminderUnit
)

fun CreateScheduledPayment.toDto(): CreateScheduledPaymentDto {
    return CreateScheduledPaymentDto(
        title = this.title,
        amount = this.amount,
        categoryId = this.categoryId,
        startDate = this.startDate.toLocalDateTime().toUtcInstant().toString(),
        frequency = if (this.frequency == PaymentFrequency.CUSTOM) this.customFrequencyDays ?: 0 else this.frequency.toDays(),
        reminderPeriod = this.reminderPeriod,
        reminderUnit = this.reminderUnit
    )
}
