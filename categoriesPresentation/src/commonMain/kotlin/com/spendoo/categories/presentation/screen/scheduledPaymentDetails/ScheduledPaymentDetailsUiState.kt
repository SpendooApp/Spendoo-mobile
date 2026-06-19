package com.spendoo.categories.presentation.screen.scheduledPaymentDetails

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet.AddScheduledPaymentUiState
import com.spendoo.shared.domain.utils.getToday
import com.spendoo.designsystem.utils.UiText
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.overdue
import spendoo.designsystem.generated.resources.today
import spendoo.designsystem.generated.resources.tomorrow
import spendoo.designsystem.generated.resources.days_left

data class ScheduledPaymentDetailsUiState(
    val isLoading: Boolean = true,
    val isEditBottomSheetVisible: Boolean = false,
    val paymentId: String = "",
    val title: String = "",
    val amount: String = "",
    val categoryId: String = "",
    val categoryIcon: CategoryIcon = CategoryIcon.DEFAULT,
    val startDate: LocalDate = getToday(),
    val frequency: PaymentFrequency = PaymentFrequency.DAILY,
    val customFrequencyDays: Int? = null,
    val reminderPeriod: Int = 0,
    val reminderUnit: ReminderUnit = ReminderUnit.DAY,
    val dueDate: String = "",
    val timeLeft: UiText = UiText.DynamicString(""),
    val isDueSoon: Boolean = false
)

fun ScheduledPaymentDetailsUiState.toAddScheduledPaymentUiState(): AddScheduledPaymentUiState {
    return AddScheduledPaymentUiState(
        isEditing = true,
        paymentId = paymentId,
        title = title,
        amount = amount,
        categoryIcon = categoryIcon,
        categoryId = categoryId,
        startDate = startDate,
        frequency = frequency,
        customFrequencyDays = customFrequencyDays?.toString() ?: "",
        reminderPeriodValue = reminderPeriod.toString(),
        reminderPeriodUnit = reminderUnit
    )
}

fun Long.toTimeLeftText(): UiText { //TODO: make real time lift even if hour
    return when {
        this < 0L -> UiText.StringRes(Res.string.overdue)
        this == 0L -> UiText.StringRes(Res.string.today)
        this == 1L -> UiText.StringRes(Res.string.tomorrow)
        else -> UiText.StringRes(Res.string.days_left, this.toString())
    }
}
