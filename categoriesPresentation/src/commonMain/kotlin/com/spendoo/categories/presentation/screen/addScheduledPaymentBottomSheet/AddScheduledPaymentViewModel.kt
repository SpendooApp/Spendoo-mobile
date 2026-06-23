package com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet

import com.spendoo.categories.domain.entity.scheduledPayment.CreateScheduledPayment
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.shared.domain.utils.toCleanDoubleOrNull
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.custom_frequency_must_be_greater_than_zero

class AddScheduledPaymentViewModel(
    private val repository: ScheduledPaymentsRepository
) : BaseViewModel<AddScheduledPaymentUiState>(AddScheduledPaymentUiState()),
    AddScheduledPaymentInteractionListener {

    fun init(initialState: AddScheduledPaymentUiState?) {
        if (initialState != null) {
            updateState { initialState.copy(isEditing = true) }
        } else {
            updateState { AddScheduledPaymentUiState() }
        }
    }

    override fun onTitleChanged(title: String) {
        updateState { copy(title = title) }
    }

    override fun onAmountChanged(amount: String) {
        val cleanAmount = amount.filter { it.isDigit() || it == '.' }
        val finalAmount = if (cleanAmount.count { it == '.' } <= 1) {
            cleanAmount
        } else {
            val firstDecimal = cleanAmount.indexOf('.')
            cleanAmount.filterIndexed { index, char -> char != '.' || index == firstDecimal }
        }
        updateState { copy(amount = finalAmount) }
    }

    override fun onShowCategorySelectionSheet(show: Boolean) {
        updateState { copy(isCategorySelectionSheetVisible = show) }
    }

    override fun onCategorySelected(category: CategoryItemUiState) {
        updateState {
            copy(
                categoryId = category.id,
                categoryName = category.name,
                categoryIcon = category.icon,
                isCategorySelectionSheetVisible = false
            )
        }
    }

    override fun onStartDateChanged(date: LocalDate) {
        updateState { copy(startDate = date, showDatePicker = false) }
    }

    override fun onShowDatePicker(show: Boolean) {
        updateState { copy(showDatePicker = show) }
    }

    override fun onFrequencyChanged(frequency: PaymentFrequency) {
        updateState {
            val customDays =
                if (frequency == PaymentFrequency.CUSTOM) customFrequencyDays.toIntOrNull() else null
            val availableUnits = getAvailableReminderUnits(frequency, customDays)
            val newUnit =
                if (reminderPeriodUnit in availableUnits) reminderPeriodUnit else availableUnits.firstOrNull()
                    ?: ReminderUnit.DAY

            copy(
                frequency = frequency,
                reminderPeriodUnit = newUnit,
                customFrequencyDaysError = null,
                reminderPeriodValueError = null
            )
        }
    }

    override fun onCustomFrequencyDaysChanged(days: String) {
        if (days.isEmpty() || days.all { it.isDigit() }) {
            updateState {
                val customDays = days.toIntOrNull()
                val availableUnits = getAvailableReminderUnits(frequency, customDays)
                val newUnit =
                    if (reminderPeriodUnit in availableUnits) reminderPeriodUnit else availableUnits.firstOrNull()
                        ?: ReminderUnit.DAY

                copy(
                    customFrequencyDays = days,
                    reminderPeriodUnit = newUnit,
                    customFrequencyDaysError = null,
                    reminderPeriodValueError = null
                )
            }
        }
    }

    override fun onReminderPeriodValueChanged(value: String) {
        if (value.isEmpty() || value.all { it.isDigit() }) {
            updateState {
                copy(
                    reminderPeriodValue = value,
                    reminderPeriodValueError = null
                )
            }
        }
    }

    override fun onReminderPeriodUnitChanged(unit: ReminderUnit) {
        updateState {
            copy(
                reminderPeriodUnit = unit,
                reminderPeriodValueError = null
            )
        }
    }

    override fun onShowReminderUnitDropdown(show: Boolean) {
        updateState { copy(showReminderUnitDropdown = show) }
    }

    override fun onSubmit(onSuccess: () -> Unit) {
        val currentState = state.value
        val customDays =
            if (currentState.frequency == PaymentFrequency.CUSTOM) currentState.customFrequencyDays.toIntOrNull() else null
        val customError =
            if (currentState.frequency == PaymentFrequency.CUSTOM && (customDays ?: 0) <= 0) {
                Res.string.custom_frequency_must_be_greater_than_zero
            } else null
        val reminderError = validateReminderPeriod(
            currentState.reminderPeriodValue,
            currentState.reminderPeriodUnit
        )

        if (customError != null || reminderError != null) {
            updateState {
                copy(
                    customFrequencyDaysError = customError,
                    reminderPeriodValueError = reminderError
                )
            }
            return
        }

        tryToCall(
            block = {
                val amount = currentState.amount.toCleanDoubleOrNull() ?: 0.0

                val payment = CreateScheduledPayment(
                    title = currentState.title,
                    amount = amount,
                    categoryId = currentState.categoryId,
                    startDate = currentState.startDate,
                    frequency = currentState.frequency,
                    customFrequencyDays = customDays,
                    reminderPeriod = currentState.reminderPeriodValue.toIntOrNull() ?: 0,
                    reminderUnit = currentState.reminderPeriodUnit
                )
                val paymentId = currentState.paymentId
                if (currentState.isEditing) {
                    if (paymentId != null) {
                        repository.updateScheduledPayment(paymentId, payment)
                    }
                } else {
                    repository.addScheduledPayment(payment)
                }
            },
            onSuccess = {
                onSuccess()
            },
            onError = { throwable ->
                val errorMsg = throwable.message
                showSnackBar(
                    title = if (errorMsg.isNullOrBlank()) {
                        UiText.StringRes(Res.string.an_error_occurred)
                    } else {
                        UiText.DynamicString(errorMsg)
                    },
                    isSuccess = false
                )
            }
        )
    }
}
