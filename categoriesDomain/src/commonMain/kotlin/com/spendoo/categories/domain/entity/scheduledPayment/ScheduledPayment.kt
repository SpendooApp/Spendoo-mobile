package com.spendoo.categories.domain.entity.scheduledPayment

import com.spendoo.shared.domain.entity.CategoryIcon
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class ScheduledPayment(
    val id: String,
    val title: String,
    val amount: Double,
    val categoryId: String,
    val categoryIcon: CategoryIcon,
    val startDate: LocalDateTime,
    val nextDueDate: LocalDateTime,
    val nextReminderDate: LocalDate,
    val frequency: PaymentFrequency,
    val customFrequencyDays: Int? = null,
    val reminderPeriod: Int,
    val reminderUnit: ReminderUnit
)
