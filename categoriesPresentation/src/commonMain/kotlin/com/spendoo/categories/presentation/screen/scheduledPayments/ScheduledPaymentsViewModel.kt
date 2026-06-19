package com.spendoo.categories.presentation.screen.scheduledPayments

import androidx.lifecycle.viewModelScope
import com.spendoo.categories.api.ScheduledPaymentDetailsRoute
import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.getToday
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred

class ScheduledPaymentsViewModel(
    private val scheduledPaymentsRepository: ScheduledPaymentsRepository
) : BaseViewModel<ScheduledPaymentsUiState>(ScheduledPaymentsUiState()),
    ScheduledPaymentsInteractionListener {

    private val paginator = createPaginator(
        initialKey = INITIAL_PAGE,
        pageSize = PAGE_SIZE,
        loadPage = { page ->
            scheduledPaymentsRepository.getScheduledPayments(
                PageQuery(
                    page = page,
                    size = PAGE_SIZE
                )
            ).data
        },
        onSuccess = { items ->
            val today = getToday()
            updateState {
                copy(
                    scheduledPayments = scheduledPayments + items.map { payment ->
                        val daysDiff = payment.nextDueDate.toEpochDays() - today.toEpochDays()
                        ScheduledPaymentUiState(
                            id = payment.id,
                            name = payment.title,
                            amount = payment.amount.toString(),
                            categoryIcon = payment.categoryIcon,
                            timeLeft = daysDiff.toTimeLeftText(),
                            isDueSoon = daysDiff <= 1L,
                            startDate = payment.startDate,
                            categoryId = payment.categoryId,
                            frequency = payment.frequency,
                            customFrequencyDays = payment.customFrequencyDays?.toString() ?: "",
                            reminderPeriodValue = payment.reminderPeriod.toString(),
                            reminderPeriodUnit = payment.reminderUnit
                        )
                    },
                    isScheduledPaymentsLoading = false,
                    isScheduledPaymentsLoadingMore = false
                )
            }
        },
        onLoadUpdated = { isLoading ->
            updateState {
                if (scheduledPayments.isEmpty()) {
                    copy(isScheduledPaymentsLoading = isLoading)
                } else {
                    copy(isScheduledPaymentsLoadingMore = isLoading)
                }
            }
        },
        onError = {
            updateState {
                copy(
                    isScheduledPaymentsLoading = false,
                    isScheduledPaymentsLoadingMore = false
                )
            }
        }
    )

    init {
        listenToResetSignal()
        onReload()
    }

    private fun listenToResetSignal() {
        tryToCollect(
            block = {
                getResult<Boolean?>("resetScheduledPayments", consume = true)
            },
            onEach = { shouldReset ->
                if (shouldReset == true) {
                    onReload()
                }
            },
            onError = {}
        )
    }

    override fun onReload() {
        paginator.reset()
        updateState { copy(scheduledPayments = emptyList()) }
        viewModelScope.launch {
            paginator.loadNextItems()
        }

        tryToCall(
            block = { scheduledPaymentsRepository.getScheduledPaymentsSummary() },
            onSuccess = { summary ->
                updateState {
                    copy(
                        summary = ScheduledPaymentSummaryUiState(
                            totalScheduledAmount = summary.totalScheduledAmount,
                            upcomingCount = summary.upcomingCount
                        ),
                        isSummaryLoading = false
                    )
                }
            },
            onError = {
                updateState { copy(isSummaryLoading = false) }
            },
            onStart = {
                updateState { copy(isSummaryLoading = true) }
            }
        )
    }

    override fun onBackClicked() {
        popBackStack()
    }

    override fun onAddScheduledPaymentClicked() {
        updateState { copy(isAddScheduledPaymentBottomSheetVisible = true, paymentToEdit = null) }
    }

    override fun onAddScheduledPaymentBottomSheetDismissed() {
        updateState { copy(isAddScheduledPaymentBottomSheetVisible = false, paymentToEdit = null) }
    }

    override fun setScheduledPaymentToEdit(payment: ScheduledPaymentUiState) {
        updateState {
            copy(
                paymentToEdit = payment,
                isAddScheduledPaymentBottomSheetVisible = true
            )
        }
    }

    override fun onScheduledPaymentActionsSheetDismissed() {
        updateState { copy(isScheduledPaymentActionsSheetVisible = false) }
    }

    override fun onListScrolled() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    override fun navigateToDetails(paymentId: String) {
        navigate(ScheduledPaymentDetailsRoute(paymentId))
    }

    override fun onSkipPayment(paymentId: String) {
        tryToCall(
            block = { scheduledPaymentsRepository.skipScheduledPayment(paymentId) },
            onSuccess = { onReload() },
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }

    override fun onPayPayment(paymentId: String) {
        tryToCall(
            block = { scheduledPaymentsRepository.payScheduledPayment(paymentId) },
            onSuccess = { onReload() },
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }

    companion object {
        const val INITIAL_PAGE = 0
        const val PAGE_SIZE = 20
    }
}
