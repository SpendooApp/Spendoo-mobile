package com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
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
    val startDate: LocalDate? = null,
    val showDatePicker: Boolean = false,
    val frequency: PaymentFrequency = PaymentFrequency.DAILY,
    val reminderPeriodValue: String = "",
    val reminderPeriodUnit: ReminderUnit = ReminderUnit.DAY,
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
    }
}
