package com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet

import com.spendoo.categories.domain.entity.scheduledPayment.CreateScheduledPayment
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.domain.entity.scheduledPayment.ReminderUnit
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState
import com.spendoo.categories.presentation.shared.toCleanDoubleOrNull
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.getString
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.start_date_is_required

class AddScheduledPaymentViewModel(
    private val repository: ScheduledPaymentsRepository,
    private val categoriesRepository: CategoriesRepository
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
        updateState { copy(amount = amount) }
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
        updateState { copy(frequency = frequency) }
    }

    override fun onReminderPeriodValueChanged(value: String) {
        if (value.isEmpty() || value.all { it.isDigit() }) {
            updateState { copy(reminderPeriodValue = value) }
        }
    }

    override fun onReminderPeriodUnitChanged(unit: ReminderUnit) {
        updateState { copy(reminderPeriodUnit = unit) }
    }

    override fun onShowReminderUnitDropdown(show: Boolean) {
        updateState { copy(showReminderUnitDropdown = show) }
    }

    override fun onSubmit(onSuccess: () -> Unit) {
        tryToCall(
            block = {
                val amount = state.value.amount.toCleanDoubleOrNull() ?: 0.0
                val startDate = state.value.startDate
                    ?: throw Exception(getString(Res.string.start_date_is_required))
                val payment = CreateScheduledPayment(
                    title = state.value.title,
                    amount = amount,
                    categoryId = state.value.categoryId,
                    startDate = startDate,
                    frequency = state.value.frequency,
                    reminderPeriod = state.value.reminderPeriodValue.toIntOrNull() ?: 0,
                    reminderUnit = state.value.reminderPeriodUnit
                )
                val paymentId = state.value.paymentId
                if (state.value.isEditing) {
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
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }
}
