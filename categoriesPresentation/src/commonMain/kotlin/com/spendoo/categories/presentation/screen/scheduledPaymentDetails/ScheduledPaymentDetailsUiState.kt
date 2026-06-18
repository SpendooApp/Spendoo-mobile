package com.spendoo.categories.presentation.screen.scheduledPaymentDetails

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.designsystem.utils.UiText
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.overdue
import spendoo.designsystem.generated.resources.today
import spendoo.designsystem.generated.resources.tomorrow
import spendoo.designsystem.generated.resources.days_left

data class ScheduledPaymentDetailsUiState(
    val isLoading: Boolean = true,
    val paymentId: String = "",
    val title: String = "",
    val amount: String = "",
    val categoryIcon: CategoryIcon = CategoryIcon.DEFAULT,
    val frequency: PaymentFrequency = PaymentFrequency.DAILY,
    val dueDate: String = "",
    val timeLeft: UiText = UiText.DynamicString(""),
    val isDueSoon: Boolean = false
)

fun Long.toTimeLeftText(): UiText {
    return when {
        this < 0L -> UiText.StringRes(Res.string.overdue)
        this == 0L -> UiText.StringRes(Res.string.today)
        this == 1L -> UiText.StringRes(Res.string.tomorrow)
        else -> UiText.StringRes(Res.string.days_left, this.toString())
    }
}
