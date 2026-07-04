package com.spendoo.categories.presentation.screen.editTransactionBottomSheet

import com.spendoo.categories.domain.entity.transaction.TransactionType
import com.spendoo.categories.domain.entity.transaction.UpdateTransaction
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.shared.domain.utils.toCleanDoubleOrNull
import com.spendoo.shared.domain.utils.toLocalDateTime
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.enter_a_title
import spendoo.designsystem.generated.resources.enter_a_valid_amount
import spendoo.designsystem.generated.resources.enter_your_amount
import spendoo.designsystem.generated.resources.note_too_long
import spendoo.designsystem.generated.resources.please_select_a_category
import spendoo.designsystem.generated.resources.title_too_long
import spendoo.designsystem.generated.resources.title_too_short
import kotlin.math.absoluteValue

class EditTransactionViewModel(
    private val transactionId: String,
    private val transactionsRepository: TransactionsRepository
) : BaseViewModel<EditTransactionUiState>(EditTransactionUiState(transactionId = transactionId)), 
    EditTransactionInteractionListener {

    init {
        loadTransaction()
    }

    private fun loadTransaction() {
        tryToCall(
            block = { transactionsRepository.getTransaction(transactionId) },
            onStart = { updateState { it.copy(isLoading = true) } },
            onSuccess = { transaction ->
                updateState {
                    it.copy(
                        title = transaction.title,
                        transactionType = transaction.type,
                        amount = transaction.amount.absoluteValue,
                        note = transaction.note ?: "",
                        date = transaction.date.date,
                        categoryId = transaction.category?.categoryId,
                        categoryName = transaction.category?.categoryName ?: "",
                        categoryIcon = transaction.category?.categoryIcon,
                        isLoading = false
                    )
                }
            },
            onError = { throwable ->
                updateState { it.copy(isLoading = false) }
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    override fun onTitleChanged(title: String) {
        updateState { it.copy(title = title, titleError = null) }
    }

    override fun onAmountChanged(amount: String) {
        updateState { it.copy(amount = amount.toCleanDoubleOrNull(), amountError = null) }
    }

    override fun onNoteChanged(note: String) {
        updateState { it.copy(note = note, noteError = null) }
    }

    override fun onDateSelected(date: LocalDate) {
        updateState { it.copy(date = date, showDatePicker = false) }
    }

    override fun onDatePickerRequested() {
        updateState { it.copy(showDatePicker = true) }
    }

    override fun onDatePickerDismissed() {
        updateState { it.copy(showDatePicker = false) }
    }

    override fun onCategoryFieldClicked() {
        updateState { it.copy(showCategorySheet = true) }
    }

    override fun onCategorySelected(category: CategoryItemUiState) {
        updateState {
            it.copy(
                categoryId = category.id,
                categoryName = category.name,
                categoryIcon = category.icon,
                categoryError = null,
                showCategorySheet = false
            )
        }
    }

    override fun onCategorySheetDismissed() {
        updateState { it.copy(showCategorySheet = false) }
    }

    private fun validateFields(): Boolean {
        var hasErrors = false
        val titleErr = when {
            state.value.title.isBlank() -> UiText.StringRes(Res.string.enter_a_title)
            state.value.title.length < 2 -> UiText.StringRes(Res.string.title_too_short)
            state.value.title.length > 50 -> UiText.StringRes(Res.string.title_too_long)
            else -> null
        }
        val amountValue = state.value.amount
        val amountErr = when {
            state.value.amount == null -> UiText.StringRes(Res.string.enter_your_amount)
            amountValue == null || amountValue <= 0 -> UiText.StringRes(Res.string.enter_a_valid_amount)
            else -> null
        }
        val noteErr = when {
            state.value.note.length > 200 -> UiText.StringRes(Res.string.note_too_long)
            else -> null
        }
        val categoryErr = when {
            state.value.transactionType == TransactionType.EXPENSE &&
                    state.value.categoryId == null -> UiText.StringRes(Res.string.please_select_a_category)

            else -> null
        }

        if (titleErr != null || amountErr != null || noteErr != null || categoryErr != null) {
            hasErrors = true
        }

        updateState {
            it.copy(
                titleError = titleErr,
                amountError = amountErr,
                noteError = noteErr,
                categoryError = categoryErr
            )
        }

        return !hasErrors
    }

    override fun saveTransaction() {
        if (!validateFields()) return

        val amountValue = state.value.amount ?: 0.0
        val request = UpdateTransaction(
            title = state.value.title,
            transactionDate = state.value.date.toLocalDateTime().toString(),
            note = state.value.note.takeIf { it.isNotBlank() },
            amount = amountValue,
            categoryId = state.value.categoryId
        )

        tryToCall(
            block = { transactionsRepository.updateTransaction(transactionId, request) },
            onStart = { updateState { it.copy(isSaving = true) } },
            onSuccess = {
                popBackStack("refreshTransactions" to true)
            },
            onError = { throwable ->
                updateState { it.copy(isSaving = false) }
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    override fun onSheetHidden() {
        popBackStack()
    }
}
