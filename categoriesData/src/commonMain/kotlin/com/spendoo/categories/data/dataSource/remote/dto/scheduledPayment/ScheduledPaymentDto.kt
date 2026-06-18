package com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPayment
import com.spendoo.categories.data.mapper.toLocalDateTimeOrDefault
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ScheduledPaymentDto(
    val id: String,
    val title: String,
    val amount: Double,
    val categoryId: String,
    val categoryIcon: String? = null,
    val startDate: String,
    val nextDueDate: String,
    val nextReminderDate: String,
    val frequency: Int,
    val reminderPeriod: Int,
    val reminderUnit: ReminderUnit
)

fun ScheduledPaymentDto.toDomain(): ScheduledPayment {
    return ScheduledPayment(
        id = this.id,
        title = this.title,
        amount = this.amount,
        categoryId = this.categoryId,
        categoryIcon = CategoryIcon.fromStringOrDefault(this.categoryIcon),
        startDate = this.startDate.toLocalDateTimeOrDefault().date,
        nextDueDate = this.nextDueDate.toLocalDateTimeOrDefault().date,
        nextReminderDate = this.nextReminderDate.toLocalDateTimeOrDefault().date,
        frequency = PaymentFrequency.fromDays(this.frequency),
        reminderPeriod = this.reminderPeriod,
        reminderUnit = this.reminderUnit
    )
}
