package com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet

import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import kotlinx.datetime.LocalDate
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState

interface AddScheduledPaymentInteractionListener {
    fun onTitleChanged(title: String)
    fun onAmountChanged(amount: String)
    fun onShowCategorySelectionSheet(show: Boolean)
    fun onCategorySelected(category: CategoryItemUiState)
    fun onStartDateChanged(date: LocalDate)
    fun onShowDatePicker(show: Boolean)
    fun onFrequencyChanged(frequency: PaymentFrequency)
    fun onCustomFrequencyDaysChanged(days: String)
    fun onReminderPeriodValueChanged(value: String)
    fun onReminderPeriodUnitChanged(unit: ReminderUnit)
    fun onShowReminderUnitDropdown(show: Boolean)
    fun onSubmit(onSuccess: () -> Unit)
}
