package com.spendoo.categories.domain.entity.scheduledPayment

import kotlinx.datetime.LocalDate

data class CreateScheduledPayment(
    val title: String,
    val amount: Double,
    val categoryId: String,
    val startDate: LocalDate,
    val frequency: PaymentFrequency,
    val customFrequencyDays: Int? = null,
    val reminderPeriod: Int,
    val reminderUnit: ReminderUnit
)
