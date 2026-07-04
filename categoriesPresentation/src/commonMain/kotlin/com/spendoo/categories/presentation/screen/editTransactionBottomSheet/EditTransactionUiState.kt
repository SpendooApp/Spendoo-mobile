package com.spendoo.categories.presentation.screen.editTransactionBottomSheet

import com.spendoo.categories.domain.entity.transaction.TransactionType
import com.spendoo.designsystem.utils.UiText
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.utils.getToday
import kotlinx.datetime.LocalDate

data class EditTransactionUiState(
    val transactionId: String = "",
    val title: String = "",
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val amount: Double? = null,
    val note: String = "",
    val date: LocalDate = getToday(),
    val categoryId: String? = null,
    val categoryName: String = "",
    val categoryIcon: CategoryIcon? = null,
    val isSaving: Boolean = false,
    val isLoading: Boolean = true,
    val showDatePicker: Boolean = false,
    val showCategorySheet: Boolean = false,
    val titleError: UiText? = null,
    val amountError: UiText? = null,
    val noteError: UiText? = null,
    val categoryError: UiText? = null,
) {
    val isTitleValid: Boolean
        get() = title.isNotBlank() && title.length in 2..50

    val isAmountValid: Boolean
        get() = amount != null && amount > 0

    val isNoteValid: Boolean
        get() = note.length <= 200

    val isValid: Boolean
        get() = isTitleValid && isAmountValid && isNoteValid
}
