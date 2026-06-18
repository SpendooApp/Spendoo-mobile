package com.spendoo.categories.presentation.screen.scheduledPaymentDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.spendoo.categories.api.ScheduledPaymentDetailsRoute
import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import com.spendoo.categories.presentation.shared.getToday
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.extentions.format
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred

class ScheduledPaymentDetailsViewModel(
    private val scheduledPaymentsRepository: ScheduledPaymentsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ScheduledPaymentDetailsUiState>(ScheduledPaymentDetailsUiState()),
    ScheduledPaymentDetailsInteractionListener {

    private val paymentId: String =
        savedStateHandle.toRoute<ScheduledPaymentDetailsRoute>().paymentId

    init {
        loadPaymentDetails()
    }

    private fun loadPaymentDetails() {
        tryToCall(
            block = {
                scheduledPaymentsRepository.getScheduledPayment(paymentId)
            },
            onSuccess = { payment ->
                val today = getToday()
                val daysDiff = payment.startDate.toEpochDays() - today.toEpochDays()

                updateState {
                    copy(
                        isLoading = false,
                        paymentId = payment.id,
                        title = payment.title,
                        amount = payment.amount.toString(),
                        categoryIcon = payment.categoryIcon,
                        frequency = payment.frequency,
                        dueDate = payment.startDate.format(),
                        timeLeft = daysDiff.toTimeLeftText()
                    )
                }
            },
            onError = {
                updateState { copy(isLoading = false) }
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }

    override fun onBackClicked() {
        popBackStack()
    }

    override fun onEditClicked() {
        navigate(ScheduledPaymentDetailsRoute(paymentId)) // Update when edit route exists
    }

    override fun onDeleteClicked() {
        tryToCall(
            block = {
                scheduledPaymentsRepository.deleteScheduledPayment(paymentId)
            },
            onSuccess = {
                popBackStack()
            },
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }

    override fun onSkipClicked() {
        tryToCall(
            block = { scheduledPaymentsRepository.skipScheduledPayment(paymentId) },
            onSuccess = { popBackStack() },
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }

    override fun onPayClicked() {
        tryToCall(
            block = { scheduledPaymentsRepository.payScheduledPayment(paymentId) },
            onSuccess = { popBackStack() },
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }
}
