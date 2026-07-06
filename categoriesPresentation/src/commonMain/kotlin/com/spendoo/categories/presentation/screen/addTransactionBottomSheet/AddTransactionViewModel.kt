package com.spendoo.categories.presentation.screen.addTransactionBottomSheet

import androidx.lifecycle.viewModelScope
import com.spendoo.categories.domain.entity.transaction.CreateExpense
import com.spendoo.categories.domain.entity.transaction.CreateIncome
import com.spendoo.categories.domain.entity.transaction.ExpenseEntry
import com.spendoo.categories.domain.entity.transaction.IncomeEntry
import com.spendoo.categories.domain.entity.transaction.ReadyTransactionEntry
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.shared.domain.utils.toLocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.LocalDateTime
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.toUiText
import com.spendoo.goals.domain.repository.GoalsRepository
import com.spendoo.shared.domain.utils.toCleanDoubleOrNull
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import com.spendoo.categories.presentation.compressImage
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
import com.spendoo.shared.domain.entity.CategoryIcon
import spendoo.designsystem.generated.resources.enter_a_title
import spendoo.designsystem.generated.resources.enter_your_amount
import spendoo.designsystem.generated.resources.title_too_short
import spendoo.designsystem.generated.resources.title_too_long
import spendoo.designsystem.generated.resources.enter_a_valid_amount
import spendoo.designsystem.generated.resources.note_too_long
import spendoo.designsystem.generated.resources.please_select_a_category

class AddTransactionViewModel(
    private val transactionsRepository: TransactionsRepository,
    private val goalsRepository: GoalsRepository,
) : BaseViewModel<AddTransactionUiState>(AddTransactionUiState()),
    AddTransactionInteractionListener {

    init {
        loadSavedExpenseTitles()
    }

    private fun loadSavedExpenseTitles() {
        tryToCall(
            block = { transactionsRepository.getSavedExpenseTitles() },
            onSuccess = { titles ->
                updateState { copy(savedExpenseTitles = titles) }
            },
            onError = {}
        )
    }


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
        updateState { it.copy(incomeTitle = title, incomeTitleError = null) }
    }

    override fun onIncomeAmountChanged(amount: String) {
        updateState { it.copy(incomeAmount = amount.toCleanDoubleOrNull(), incomeAmountError = null) }
    }

    override fun onSavingAmountChanged(amount: String) {
        updateState { it.copy(savingAmount = amount.toCleanDoubleOrNull(), savingAmountError = null) }
    }

    override fun onNoteChanged(note: String) {
        updateState { it.copy(incomeNote = note, incomeNoteError = null) }
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

    override fun onTimeSelected(time: LocalTime) {
        updateState { it.copy(time = time, showTimePicker = false) }
    }

    override fun onTimePickerRequested() {
        updateState { it.copy(showTimePicker = true) }
    }

    override fun onTimePickerDismissed() {
        updateState { it.copy(showTimePicker = false) }
    }

    override fun onSavingChecked(checked: Boolean) {
        updateState { it.copy(isSavingChecked = checked, savingAmountError = null) }
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
                if (existing.id == id) {
                    entry.copy(
                        titleError = if (entry.title != existing.title) null else existing.titleError,
                        amountError = if (entry.amount != existing.amount) null else existing.amountError,
                        noteError = if (entry.note != existing.note) null else existing.noteError,
                        categoryError = if (entry.categoryId != existing.categoryId) null else existing.categoryError
                    )
                } else existing
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
                    categoryIcon = category.icon,
                    categoryError = null
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
                updateState { copy(isProcessingMedia = true) }
                val bytes = it.readBytes()
                val originalSize = bytes.size
                val compressed = withContext(Dispatchers.Default) {
                    compressImage(bytes)
                }
                val compressedSize = compressed.size
                println("Image Compression: Before = ${originalSize / 1024} KB, After = ${compressedSize / 1024} KB")
                onImageProcessed(compressed)
            }
        }
    }

    private fun processInput(call: suspend () -> List<ReadyTransactionEntry>) {
        updateState { it.copy(isProcessingMedia = true) }
        tryToCall(
            block = { call() },
            onSuccess = { readyEntries ->
                val uiEntries = readyEntries.map { ready ->
                    TransactionEntryUiState(
                        title = ready.title,
                        amount = ready.amount,
                        categoryId = ready.categoryId,
                        categoryName = ready.categoryName,
                        categoryIcon = ready.categoryIcon,
                        note = ready.note.orEmpty()
                    )
                }
                updateState {
                    it.copy(
                        expenseEntries = it.expenseEntries + uiEntries,
                        isProcessingMedia = false
                    )
                }
            },
            onError = { throwable ->
                updateState { it.copy(isProcessingMedia = false) }
                showSnackBar(
                    Res.string.error_processing_input.toUiText(),
                    throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false,
                )
            }
        )
    }

    private fun validateEntryTitle(title: String): UiText? = when {
        title.isBlank() -> UiText.StringRes(Res.string.enter_a_title)
        title.length < 2 -> UiText.StringRes(Res.string.title_too_short)
        title.length > 50 -> UiText.StringRes(Res.string.title_too_long)
        else -> null
    }

    private fun validateEntryAmount(amount: Double?): UiText? = when {
        amount == null -> UiText.StringRes(Res.string.enter_your_amount)
        amount <= 0 -> UiText.StringRes(Res.string.enter_a_valid_amount)
        else -> null
    }

    private fun validateEntryNote(note: String): UiText? = when {
        note.length > 200 -> UiText.StringRes(Res.string.note_too_long)
        else -> null
    }

    private fun validateEntryCategory(categoryId: String?, categoryName: String?, categoryIcon: CategoryIcon?): UiText? {
        val hasCategory = categoryId != null && categoryName != null && categoryIcon != null
        return if (!hasCategory) UiText.StringRes(Res.string.please_select_a_category) else null
    }

    private fun validateExpenseEntries(): Boolean {
        var hasErrors = false
        val validatedEntries = state.value.expenseEntries.map { entry ->
            val titleErr = validateEntryTitle(entry.title)
            val amountErr = validateEntryAmount(entry.amount)
            val noteErr = validateEntryNote(entry.note)
            val categoryErr = validateEntryCategory(entry.categoryId, entry.categoryName, entry.categoryIcon)
            
            if (titleErr != null || amountErr != null || noteErr != null || categoryErr != null) {
                hasErrors = true
            }
            entry.copy(
                titleError = titleErr,
                amountError = amountErr,
                noteError = noteErr,
                categoryError = categoryErr
            )
        }
        updateState { copy(expenseEntries = validatedEntries) }
        return !hasErrors
    }

    private fun validateIncomeForm(): Boolean {
        val titleErr = validateEntryTitle(state.value.incomeTitle)
        val amountErr = validateEntryAmount(state.value.incomeAmount)
        val noteErr = validateEntryNote(state.value.incomeNote)
        
        updateState {
            copy(
                incomeTitleError = titleErr,
                incomeAmountError = amountErr,
                incomeNoteError = noteErr
            )
        }
        return titleErr == null && amountErr == null && noteErr == null
    }

    private fun validateSavingAmount(): Boolean {
        val amountErr = if (state.value.isSavingChecked) {
            validateEntryAmount(state.value.savingAmount)
        } else null
        
        updateState {
            copy(savingAmountError = amountErr)
        }
        return amountErr == null
    }

    override fun submit() {
        val currentState = state.value
        if (currentState.isSubmitting) return

        when (currentState.type) {
            TransactionType.Income -> {
                if (currentState.isSavingChecked) {
                    val isSavingValid = validateSavingAmount()
                    if (isSavingValid) {
                        addToSaving()
                    }
                } else {
                    val isIncomeValid = validateIncomeForm()
                    if (isIncomeValid) {
                        createIncome()
                    }
                }
            }
            TransactionType.Expense -> {
                val isExpenseValid = validateExpenseEntries()
                if (isExpenseValid) {
                    createExpense()
                }
            }
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
                goalsRepository.addToSavings(amount)
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
                                transactionDate = LocalDateTime(state.value.date, state.value.time),
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
                val titles = state.value.expenseEntries.map { it.title }.filter { it.isNotBlank() }
                transactionsRepository.saveExpenseTitles(titles)
                val entries = state.value.expenseEntries.map {
                    ExpenseEntry(
                        title = it.title,
                        amount = it.amount ?: 0.0,
                        categoryId = it.categoryId ?: "",
                        transactionDate = LocalDateTime(state.value.date, state.value.time),
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
        popBackStack("reset" to true)
    }

    private fun resetForm() {
        updateState {
            AddTransactionUiState()
        }
    }
}
