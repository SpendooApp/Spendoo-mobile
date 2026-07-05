package com.spendoo.categories.presentation.screen.editTransactionBottomSheet

import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState
import kotlinx.datetime.LocalDate

interface EditTransactionInteractionListener {
    fun onSheetHidden()
    fun onTitleChanged(title: String)
    fun onAmountChanged(amount: String)
    fun onNoteChanged(note: String)
    fun onDateSelected(date: LocalDate)
    fun onDatePickerRequested()
    fun onDatePickerDismissed()
    fun onCategoryFieldClicked()
    fun onCategorySelected(category: CategoryItemUiState)
    fun onCategorySheetDismissed()
    fun saveTransaction()
}
