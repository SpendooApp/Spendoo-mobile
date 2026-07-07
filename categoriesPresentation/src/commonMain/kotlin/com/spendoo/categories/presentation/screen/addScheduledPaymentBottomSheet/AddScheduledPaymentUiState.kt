package com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.shared.domain.utils.getNow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.daily
import spendoo.designsystem.generated.resources.weekly
import spendoo.designsystem.generated.resources.monthly
import spendoo.designsystem.generated.resources.yearly
import spendoo.designsystem.generated.resources.custom
import spendoo.designsystem.generated.resources.hour
import spendoo.designsystem.generated.resources.day
import spendoo.designsystem.generated.resources.week

import org.jetbrains.compose.resources.StringResource
import spendoo.designsystem.generated.resources.reminder_days_limit
import spendoo.designsystem.generated.resources.reminder_hours_limit
import spendoo.designsystem.generated.resources.reminder_months_limit
import spendoo.designsystem.generated.resources.reminder_weeks_limit

data class AddScheduledPaymentUiState(
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val paymentId: String? = null,
    val title: String = "",
    val amount: String = "",
    val categoryId: String = "",
    val categoryIcon: CategoryIcon = CategoryIcon.DEFAULT,
    val categoryName: String = "",
    val isCategorySelectionSheetVisible: Boolean = false,
    val startDate: LocalDateTime = getNow(),
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val frequency: PaymentFrequency = PaymentFrequency.MONTHLY,
    val customFrequencyDays: String = "",
    val customFrequencyDaysError: StringResource? = null,
    val reminderPeriodValue: String = "",
    val reminderPeriodUnit: ReminderUnit = ReminderUnit.DAY,
    val reminderPeriodValueError: StringResource? = null,
    val showReminderUnitDropdown: Boolean = false,
)

fun PaymentFrequency.toText(): StringResource {
    return when (this) {
        PaymentFrequency.DAILY -> Res.string.daily
        PaymentFrequency.WEEKLY -> Res.string.weekly
        PaymentFrequency.MONTHLY -> Res.string.monthly
        PaymentFrequency.YEARLY -> Res.string.yearly
        PaymentFrequency.CUSTOM -> Res.string.custom
    }
}

fun ReminderUnit.toText(): StringResource {
    return when (this) {
        ReminderUnit.HOUR -> Res.string.hour
        ReminderUnit.DAY -> Res.string.day
        ReminderUnit.WEEK -> Res.string.week
        ReminderUnit.MONTH -> Res.string.monthly
    }
}

fun getAvailableReminderUnits(frequency: PaymentFrequency, customDays: Int?): List<ReminderUnit> {
    val frequencyInDays = when (frequency) {
        PaymentFrequency.DAILY -> 1
        PaymentFrequency.WEEKLY -> 7
        PaymentFrequency.MONTHLY -> 30
        PaymentFrequency.YEARLY -> 365
        PaymentFrequency.CUSTOM -> customDays ?: 0
    }
    
    return ReminderUnit.entries.filter { unit ->
        val unitDays = when (unit) {
            ReminderUnit.HOUR -> 0.04
            ReminderUnit.DAY -> 1.0
            ReminderUnit.WEEK -> 7.0
            ReminderUnit.MONTH -> 30.0
        }
        frequencyInDays <= 0 || unitDays < frequencyInDays
    }.sortedByDescending { it.ordinal }
}

fun validateReminderPeriod(value: String, unit: ReminderUnit): StringResource? {
    val reminderValue = value.toIntOrNull() ?: return null
    return when (unit) {
        ReminderUnit.HOUR -> if (reminderValue >= 24) Res.string.reminder_hours_limit else null
        ReminderUnit.DAY -> if (reminderValue >= 30) Res.string.reminder_days_limit else null
        ReminderUnit.WEEK -> if (reminderValue >= 4) Res.string.reminder_weeks_limit else null
        ReminderUnit.MONTH -> if (reminderValue >= 12) Res.string.reminder_months_limit else null
    }
}
