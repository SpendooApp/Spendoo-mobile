package com.spendoo.categories.presentation.screen.transactionDetails

import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred

class TransactionDetailsViewModel(
    private val transactionId: String,
    private val transactionsRepository: TransactionsRepository
) : BaseViewModel<TransactionDetailsUiState>(TransactionDetailsUiState()),
    TransactionDetailsInteractionListener {

    init {
        loadTransactionDetails()
    }

    private fun loadTransactionDetails() {
        tryToCall(
            block = {
                transactionsRepository.getTransaction(transactionId)
            },
            onSuccess = { transaction ->
                updateState {
                    transaction.toDetailsUiState()
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
        // Since custom transaction editing isn't fully routed yet, we show a info snackbar
        showSnackBar(
            title = UiText.DynamicString("Edit transaction functionality is not implemented yet."),
            isSuccess = true
        )
    }

    override fun onDeleteClicked() {
        tryToCall(
            block = {
                transactionsRepository.deleteTransaction(transactionId)
            },
            onSuccess = {
                popBackStack("reset" to true)
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
