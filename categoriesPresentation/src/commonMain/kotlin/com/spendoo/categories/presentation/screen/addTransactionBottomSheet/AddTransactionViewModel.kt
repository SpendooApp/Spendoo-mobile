package com.spendoo.categories.presentation.screen.addTransactionBottomSheet

import androidx.lifecycle.viewModelScope
import com.spendoo.categories.domain.entity.transaction.CreateExpense
import com.spendoo.categories.domain.entity.transaction.CreateIncome
import com.spendoo.categories.domain.entity.transaction.ExpenseEntry
import com.spendoo.categories.domain.entity.transaction.IncomeEntry
import com.spendoo.categories.domain.entity.transaction.ReadyTransactionEntry
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.categories.domain.utils.PageQuery
import com.spendoo.categories.domain.utils.toLocalDateTime
import com.spendoo.categories.presentation.navigation.ARG_TRANSACTION_ADDED
import com.spendoo.categories.presentation.shared.BaseViewModel
import com.spendoo.categories.presentation.shared.toCleanDoubleOrNull
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.toUiText
import com.spendoo.goals.domain.repository.GoalsRepository
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.error_adding_expenses_transaction
import spendoo.designsystem.generated.resources.error_adding_income_transaction
import spendoo.designsystem.generated.resources.error_adding_to_saving
import spendoo.designsystem.generated.resources.error_processing_input
import spendoo.designsystem.generated.resources.invalid_entries
import spendoo.designsystem.generated.resources.please_fix_errors_before_submitting
import spendoo.designsystem.generated.resources.invalid_saving_amount
import spendoo.designsystem.generated.resources.please_enter_a_valid_amount
import spendoo.designsystem.generated.resources.invalid_income_data

class AddTransactionViewModel(
    private val transactionsRepository: TransactionsRepository,
    private val goalsRepository: GoalsRepository,
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<AddTransactionUiState>(AddTransactionUiState()),
    AddTransactionInteractionListener {

    override fun onSheetHidden() {
        resetForm()
    }

    override fun onDismiss() {
        popBackStack()
    }

    override fun onTypeSelected(type: TransactionType) {
        updateState { it.copy(type = type) }
    }

    override fun onIncomeTitleChanged(title: String) {
        updateState { it.copy(incomeTitle = title) }
    }

    override fun onIncomeAmountChanged(amount: String) {
        updateState { it.copy(incomeAmount = amount.toCleanDoubleOrNull()) }
    }

    override fun onSavingAmountChanged(amount: String) {
        updateState { it.copy(savingAmount = amount.toCleanDoubleOrNull()) }
    }

    override fun onNoteChanged(note: String) {
        updateState { it.copy(incomeNote = note) }
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

    override fun onSavingChecked(checked: Boolean) {
        updateState { it.copy(isSavingChecked = checked) }
    }

    // Expense Entry Actions
    override fun addExpenseEntry() {
        updateState { it.copy(expenseEntries = it.expenseEntries + TransactionEntryUiState()) }
    }

    override fun removeExpenseEntry(id: String) {
        updateState {
            val newList = it.expenseEntries.filter { entry -> entry.id != id }
            it.copy(expenseEntries = newList.ifEmpty { listOf(TransactionEntryUiState()) })
        }
    }

    override fun onEntryChanged(id: String, entry: TransactionEntryUiState) {
        updateState {
            it.copy(expenseEntries = it.expenseEntries.map { existing ->
                if (existing.id == id) entry else existing
            })
        }
    }

    // Category Selection
    override fun onCategoryFieldClicked(entryId: String) {
        updateState { it.copy(showCategorySheet = true, selectedEntryIdForCategory = entryId) }
    }

    override fun onCategorySelected(category: CategoryItemUiState) {
        val updatedEntries = state.value.expenseEntries.map { entry ->
            if (entry.id == state.value.selectedEntryIdForCategory) {
                entry.copy(
                    categoryId = category.id,
                    categoryName = category.name,
                    categoryIcon = category.icon
                )
            } else entry
        }

        updateState {
            it.copy(
                expenseEntries = updatedEntries,
                showCategorySheet = false,
                selectedEntryIdForCategory = null
            )
        }
    }

    override fun onCategorySheetDismissed() {
        updateState { it.copy(showCategorySheet = false, selectedEntryIdForCategory = null) }
    }

    // Voice/Image Actions
    override fun onVoiceProcessed(file: ByteArray) {
        processInput { transactionsRepository.getReadyInputFromVoice(file) }
    }

    fun onImageProcessed(file: ByteArray) {
        processInput { transactionsRepository.getReadyInputFromImage(file) }
    }

    override fun onSelectImage(file: PlatformFile?) {
        viewModelScope.launch {
            file?.let { 
                val bytes = it.readBytes()
                onImageProcessed(bytes)
            }
        }
    }

    private fun processInput(call: suspend () -> List<ReadyTransactionEntry>) {
        loadCategories()
        updateState { it.copy(isSubmitting = true) }
        tryToCall(
            block = { call() },
            onSuccess = { readyEntries ->
                val uiEntries = readyEntries.map { ready ->
                    val category = state.value.categories.find { it.id == ready.categoryId }
                    TransactionEntryUiState(
                        title = ready.title,
                        amount = ready.amount,
                        categoryId = ready.categoryId,
                        categoryName = category?.name,
                        categoryIcon = category?.icon
                    )
                }
                updateState {
                    it.copy(
                        expenseEntries = uiEntries.ifEmpty { it.expenseEntries },
                        isSubmitting = false
                    )
                }
            },
            onError = { throwable ->
                updateState { it.copy(isSubmitting = false) }
                showSnackBar(
                    Res.string.error_processing_input.toUiText(),
                    throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false,
                )
            },
            onStart = {
                // TODO: Show loading indicator for voice/image processing
            },
            onEnd = {
                // TODO: Hide loading indicator
            },
        )
    }

    override fun submit() {
        val currentState = state.value
        if (currentState.isSubmitting) return

        when {
            currentState.isSavingChecked -> addToSaving()
            currentState.type == TransactionType.Income -> createIncome()
            else -> createExpense()
        }
    }

    override fun setAudioRecordingVisibility(bool: Boolean) {
        updateState { it.copy(showAudioPicker = bool) }
    }

    private fun addToSaving() {
        if (state.value.isSavingAmountValid.not()) {
            showSnackBar(
                Res.string.invalid_saving_amount.toUiText(),
                Res.string.please_enter_a_valid_amount.toUiText(),
                isSuccess = false,
            )
            return
        }

        tryToCall(
            onStart = {
                updateState { it.copy(isSubmitting = true) }
            },
            block = {
                val amount = state.value.savingAmount ?: 0.0
                goalsRepository.addToSaving(amount)
            },
            onSuccess = { onSuccess() },
            onError = { throwable ->
                showSnackBar(
                    Res.string.error_adding_to_saving.toUiText(),
                    throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false,
                )
            },
            onEnd = {
                updateState { it.copy(isSubmitting = false) }
            },
        )
    }

    private fun createIncome() {
        if (state.value.isIncomeFormValid.not()) {
            showSnackBar(
                Res.string.invalid_income_data.toUiText(),
                Res.string.please_fix_errors_before_submitting.toUiText(),
                isSuccess = false,
            )
            return
        }

        tryToCall(
            onStart = {
                updateState { it.copy(isSubmitting = true) }
            },
            block = {
                val amount = state.value.incomeAmount ?: 0.0
                transactionsRepository.createIncome(
                    CreateIncome(
                        listOf(
                            IncomeEntry(
                                title = state.value.incomeTitle,
                                amount = amount,
                                transactionDate = state.value.date.toLocalDateTime(),
                                note = state.value.incomeNote.ifBlank { null }
                            )
                        )
                    )
                )
            },
            onSuccess = { onSuccess() },
            onError = { throwable ->
                showSnackBar(
                    Res.string.error_adding_income_transaction.toUiText(),
                    throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false,
                )
            },
            onEnd = {
                updateState { it.copy(isSubmitting = false) }
            },
        )
    }

    private fun createExpense() {
        if (state.value.expenseEntries.any { !it.isValid }) {
            showSnackBar(
                Res.string.invalid_entries.toUiText(),
                Res.string.please_fix_errors_before_submitting.toUiText(),
                false,
            )
            return
        }

        tryToCall(
            onStart = {
                updateState { it.copy(isSubmitting = true) }
            },
            block = {
                val entries = state.value.expenseEntries.map {
                    ExpenseEntry(
                        title = it.title,
                        amount = it.amount ?: 0.0,
                        categoryId = it.categoryId ?: "",
                        transactionDate = state.value.date.toLocalDateTime(),
                        note = it.note.ifBlank { null }
                    )
                }
                transactionsRepository.createExpense(CreateExpense(entries))
            },
            onSuccess = { onSuccess() },
            onError = { throwable ->
                showSnackBar(
                    Res.string.error_adding_expenses_transaction.toUiText(),
                    throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false,
                )
            },
            onEnd = {
                updateState { it.copy(isSubmitting = false) }
            },
        )
    }

    fun onSuccess() {
        resetForm()
        popBackStack(ARG_TRANSACTION_ADDED to true)
    }

    private fun loadCategories() {
        //TODO pagination or get the ReadyTransactionEntry with category details in one call
        viewModelScope.launch {
            runCatching {
                categoriesRepository.getCategories(PageQuery(page = 0, size = 100)).data
            }.onSuccess { categories ->
                updateState {
                    it.copy(
                        categories = categories.map { category -> category.toCategoryItemUiState() },
                    )
                }
            }
        }
    }

    private fun resetForm() {
        updateState {
            AddTransactionUiState()
        }
    }
}
