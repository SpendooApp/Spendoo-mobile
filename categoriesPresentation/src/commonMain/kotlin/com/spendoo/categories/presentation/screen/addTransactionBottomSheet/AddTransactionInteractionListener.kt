package com.spendoo.categories.presentation.screen.addTransactionBottomSheet

import io.github.vinceglb.filekit.PlatformFile
import kotlinx.datetime.LocalDate

interface AddTransactionInteractionListener {
    fun onSheetHidden()
    fun onDismiss()
    fun onTypeSelected(type: TransactionType)
    fun onIncomeTitleChanged(title: String)
    fun onIncomeAmountChanged(amount: String)
    fun onSavingAmountChanged(amount: String)
    fun onNoteChanged(note: String)
    fun onDateSelected(date: LocalDate)
    fun onDatePickerRequested()
    fun onDatePickerDismissed()
    fun onTimeSelected(time: kotlinx.datetime.LocalTime)
    fun onTimePickerRequested()
    fun onTimePickerDismissed()
    fun onSavingChecked(checked: Boolean)

    // Expense Entry Actions
    fun addExpenseEntry()
    fun removeExpenseEntry(id: String)
    fun onEntryChanged(id: String, entry: TransactionEntryUiState)

    // Category Selection
    fun onCategoryFieldClicked(entryId: String)
    fun onCategorySelected(category: CategoryItemUiState)
    fun onCategorySheetDismissed()

    // Voice/Image Actions
    fun onVoiceProcessed(file: ByteArray)
    fun onSelectImage(file: PlatformFile?)

    // Submission
    fun submit()
    fun setAudioRecordingVisibility(bool: Boolean)
}
