package com.spendoo.categories.presentation.screen.scheduledPaymentDetails

import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import com.spendoo.shared.domain.utils.getToday
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.extentions.format
import com.spendoo.designsystem.utils.extentions.toTimeLeftText
import com.spendoo.shared.domain.utils.getNow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toInstant
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred

@OptIn(ExperimentalTime::class)
class ScheduledPaymentDetailsViewModel(
    private val paymentId: String,
    private val scheduledPaymentsRepository: ScheduledPaymentsRepository,
) : BaseViewModel<ScheduledPaymentDetailsUiState>(ScheduledPaymentDetailsUiState()),
    ScheduledPaymentDetailsInteractionListener {

    init {
        loadPaymentDetails()
    }

    private var hasChanges = false

    private fun loadPaymentDetails() {
        tryToCall(
            block = {
                scheduledPaymentsRepository.getScheduledPayment(paymentId)
            },
            onSuccess = { payment ->
                val now = getNow()
                val nowInstant = now.toInstant(TimeZone.currentSystemDefault())
                val dueInstant = payment.nextDueDate.toInstant(TimeZone.currentSystemDefault())
                val daysDiff = (dueInstant - nowInstant).inWholeDays

                updateState {
                    copy(
                        isLoading = false,
                        paymentId = payment.id,
                        title = payment.title,
                        amount = payment.amount.toString(),
                        categoryId = payment.categoryId,
                        categoryIcon = payment.categoryIcon,
                        startDate = payment.startDate,
                        frequency = payment.frequency,
                        customFrequencyDays = payment.customFrequencyDays,
                        reminderPeriod = payment.reminderPeriod,
                        reminderUnit = payment.reminderUnit,
                        dueDate = payment.nextDueDate.date.format(),
                        timeLeft = payment.nextDueDate.toTimeLeftText(now),
                        isDueSoon = daysDiff <= 1
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
        if (hasChanges) {
            popBackStack("resetScheduledPayments" to true)
        } else {
            popBackStack()
        }
    }

    override fun onEditClicked() {
        updateState { copy(isEditBottomSheetVisible = true) }
    }

    override fun onEditBottomSheetDismissed() {
        updateState { copy(isEditBottomSheetVisible = false) }
    }

    override fun onReloadDetails() {
        hasChanges = true
        loadPaymentDetails()
    }

    override fun onDeleteClicked() {
        tryToCall(
            block = {
                scheduledPaymentsRepository.deleteScheduledPayment(paymentId)
            },
            onSuccess = {
                popBackStack("resetScheduledPayments" to true)
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
            onSuccess = { popBackStack("resetScheduledPayments" to true) },
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
            onSuccess = { popBackStack("resetScheduledPayments" to true) },
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }
}
