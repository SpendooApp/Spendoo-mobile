package com.spendoo.categories.presentation.screen.transactionDetails

import com.spendoo.categories.api.EditTransactionRoute
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import kotlinx.coroutines.flow.Flow
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred

class TransactionDetailsViewModel(
    private val transactionId: String,
    private val transactionsRepository: TransactionsRepository
) : BaseViewModel<TransactionDetailsUiState>(TransactionDetailsUiState()),
    TransactionDetailsInteractionListener {

    val refreshSignal: Flow<Boolean?> = getResult("refreshTransactions", consume = false)

    init {
        loadTransactionDetails()
    }

    fun onTransactionEdited() {
        loadTransactionDetails()
    }

    fun loadTransactionDetails() {
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
        navigate(EditTransactionRoute(transactionId = transactionId))
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
