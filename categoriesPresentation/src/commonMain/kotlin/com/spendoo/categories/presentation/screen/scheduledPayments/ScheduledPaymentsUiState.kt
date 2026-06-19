package com.spendoo.categories.presentation.screen.scheduledPayments

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.PriorityOption
import com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet.AddScheduledPaymentUiState
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.shared.domain.utils.getToday
import kotlinx.datetime.LocalDate
import com.spendoo.designsystem.utils.UiText
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.overdue
import spendoo.designsystem.generated.resources.today
import spendoo.designsystem.generated.resources.tomorrow
import spendoo.designsystem.generated.resources.days_left

data class ScheduledPaymentsUiState(
    val summary: ScheduledPaymentSummaryUiState = ScheduledPaymentSummaryUiState(),
    val isSummaryLoading: Boolean = false,
    val scheduledPayments: List<ScheduledPaymentUiState> = emptyList(),
    val scheduledPaymentsEndReached: Boolean = false,
    val isScheduledPaymentsLoading: Boolean = false,
    val isScheduledPaymentsLoadingMore: Boolean = false,
    val isAddScheduledPaymentBottomSheetVisible: Boolean = false,
    val isScheduledPaymentActionsSheetVisible: Boolean = false,
    val paymentToEdit: ScheduledPaymentUiState? = null
)

data class ScheduledPaymentUiState(
    val id: String = "",
    val name: String = "",
    val amount: String = "",
    val categoryIcon: CategoryIcon = CategoryIcon.DEFAULT,
    val priority: PriorityOption = PriorityOption.MEDIUM,
    val leftOverOption: LeftOverOption = LeftOverOption.MOVE_TO_SAVINGS,
    val timeLeft: UiText = UiText.DynamicString(""),
    val isDueSoon: Boolean = false,
    val startDate: LocalDate = getToday(),
    val categoryId: String = "",
    val frequency: PaymentFrequency = PaymentFrequency.DAILY,
    val customFrequencyDays: String = "",
    val reminderPeriodValue: String = "",
    val reminderPeriodUnit: ReminderUnit = ReminderUnit.DAY,
)

data class ScheduledPaymentSummaryUiState(
    val upcomingCount: Int = 0,
    val totalScheduledAmount: Double = 0.0,
)

fun ScheduledPaymentUiState.toAddScheduledPaymentUiState(): AddScheduledPaymentUiState {
    return AddScheduledPaymentUiState(
        isEditing = true,
        paymentId = id,
        title = name,
        amount = amount,
        categoryIcon = categoryIcon,
        categoryId = categoryId,
        startDate = startDate,
        frequency = frequency,
        customFrequencyDays = customFrequencyDays,
        reminderPeriodValue = reminderPeriodValue,
        reminderPeriodUnit = reminderPeriodUnit
    )
}

fun Long.toTimeLeftText(): UiText {
    return when {
        this < 0L -> UiText.StringRes(Res.string.overdue)
        this == 0L -> UiText.StringRes(Res.string.today)
        this == 1L -> UiText.StringRes(Res.string.tomorrow)
        else -> UiText.StringRes(Res.string.days_left, this.toString())
    }
}
