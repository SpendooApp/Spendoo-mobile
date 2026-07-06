package com.spendoo.categories.domain.entity.scheduledPayment

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class CreateScheduledPayment(
    val title: String,
    val amount: Double,
    val categoryId: String,
    val startDate: LocalDateTime,
    val frequency: PaymentFrequency,
    val customFrequencyDays: Int? = null,
    val reminderPeriod: Int,
    val reminderUnit: ReminderUnit
)
