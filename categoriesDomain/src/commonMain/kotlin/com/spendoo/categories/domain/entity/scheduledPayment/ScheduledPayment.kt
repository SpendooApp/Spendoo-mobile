package com.spendoo.categories.domain.entity.scheduledPayment

import com.spendoo.categories.domain.entity.category.CategoryIcon
import kotlinx.datetime.LocalDate

data class ScheduledPayment(
    val id: String,
    val title: String,
    val amount: Double,
    val categoryId: String,
    val categoryIcon: CategoryIcon,
    val startDate: LocalDate,
    val frequency: PaymentFrequency,
    val reminderPeriodValue: Int,
    val reminderPeriodUnit: ReminderUnit
)
