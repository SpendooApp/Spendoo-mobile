package com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPayment
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
    val frequency: String,
    val reminderPeriodValue: Int,
    val reminderPeriodUnit: String
)

fun ScheduledPaymentDto.toDomain(): ScheduledPayment {
    return ScheduledPayment(
        id = this.id,
        title = this.title,
        amount = this.amount,
        categoryId = this.categoryId,
        categoryIcon = CategoryIcon.fromStringOrDefault(this.categoryIcon),
        startDate = LocalDate.parse(this.startDate),
        frequency = PaymentFrequency.entries.find { it.name == this.frequency } ?: PaymentFrequency.DAILY,
        reminderPeriodValue = this.reminderPeriodValue,
        reminderPeriodUnit = ReminderUnit.entries.find { it.name == this.reminderPeriodUnit } ?: ReminderUnit.DAY
    )
}
