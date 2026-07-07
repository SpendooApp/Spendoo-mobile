package com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPayment
import com.spendoo.shared.domain.utils.getNow
import com.spendoo.shared.domain.utils.toLocalDateTimeOrDefault
import kotlinx.serialization.Serializable

@Serializable
data class ScheduledPaymentDto(
    val id: String,
    val title: String,
    val amount: Double,
    val categoryId: String,
    val categoryIcon: String? = null,
    val startDate: String? = null,
    val nextDueDate: String? = null,
    val nextReminderDate: String? = null,
    val frequency: Int,
    val reminderPeriod: Int,
    val reminderUnit: ReminderUnit
)

fun ScheduledPaymentDto.toDomain(): ScheduledPayment {
    val domainFrequency = PaymentFrequency.fromDays(this.frequency)
    val now = getNow()
    val today = now.date
    return ScheduledPayment(
        id = this.id,
        title = this.title,
        amount = this.amount,
        categoryId = this.categoryId,
        categoryIcon = CategoryIcon.fromStringOrDefault(this.categoryIcon),
        startDate = this.startDate?.toLocalDateTimeOrDefault() ?: now,
        nextDueDate = this.nextDueDate?.toLocalDateTimeOrDefault() ?: now,
        nextReminderDate = this.nextReminderDate?.toLocalDateTimeOrDefault()?.date ?: today,
        frequency = domainFrequency,
        customFrequencyDays = if (domainFrequency == PaymentFrequency.CUSTOM) this.frequency else null,
        reminderPeriod = this.reminderPeriod,
        reminderUnit = this.reminderUnit
    )
}
