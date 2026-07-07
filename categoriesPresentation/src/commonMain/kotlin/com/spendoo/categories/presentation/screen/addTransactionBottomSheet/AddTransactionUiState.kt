package com.spendoo.categories.presentation.screen.addTransactionBottomSheet

import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.utils.getToday
import com.spendoo.shared.domain.utils.getNow
import com.spendoo.designsystem.utils.UiText
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.StringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.expenses
import spendoo.designsystem.generated.resources.income

enum class TransactionType {
    Income,
    Expense
}

data class TransactionEntryUiState(
    val id: String = kotlin.random.Random.nextInt().toString(),
    val title: String = "",
    val amount: Double? = null,
    val note: String = "",
    val categoryId: String? = null,
    val categoryName: String? = null,
    val categoryIcon: CategoryIcon? = null,
    val titleError: UiText? = null,
    val amountError: UiText? = null,
    val noteError: UiText? = null,
    val categoryError: UiText? = null,
) {
    val isValid: Boolean
        get() = isTitleValid && isAmountValid && isNoteValid

    val isTitleValid: Boolean
        get() = title.isNotBlank() && title.length in 2..50

    val isAmountValid: Boolean
        get() = amount != null && amount > 0

    val isNoteValid: Boolean
        get() = note.length <= 200

    val hasCategory: Boolean
        get() = categoryId != null && categoryName != null && categoryIcon != null
}

data class AddTransactionUiState(
    val type: TransactionType = TransactionType.Expense,
    val expenseEntries: List<TransactionEntryUiState> = listOf(TransactionEntryUiState()),
    val incomeTitle: String = "",
    val incomeAmount: Double? = null,
    val incomeNote: String = "",
    val date: LocalDate = getToday(),
    val time: LocalTime = getNow().time,
    val isSavingChecked: Boolean = false,
    val savingAmount: Double? = null,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val isSubmitting: Boolean = false,
    val isProcessingMedia: Boolean = false,
    val showCategorySheet: Boolean = false,
    val showAudioPicker: Boolean = false,
    val selectedEntryIdForCategory: String? = null,
    val transactionTypeOptions: List<TransactionType> = TransactionType.entries,
    val incomeTitleError: UiText? = null,
    val incomeAmountError: UiText? = null,
    val incomeNoteError: UiText? = null,
    val savingAmountError: UiText? = null,
    val savedExpenseTitles: List<String> = emptyList(),
) {
    val isSavingAmountValid: Boolean
        get() = !isSavingChecked || (savingAmount != null && savingAmount > 0)

    val isIncomeTitleValid: Boolean
        get() = incomeTitle.isNotBlank() && incomeTitle.length in 2..50

    val isIncomeAmountValid: Boolean
        get() = incomeAmount != null && incomeAmount > 0

    val isIncomeNoteValid: Boolean
        get() = incomeNote.length <= 200

    val isIncomeFormValid: Boolean
        get() = isIncomeTitleValid && isIncomeAmountValid && isIncomeNoteValid && isSavingAmountValid
}

data class CategoryItemUiState(
    val id: String,
    val name: String,
    val icon: CategoryIcon,
)

fun Category.toCategoryItemUiState() = CategoryItemUiState(
    id = categoryId,
    name = categoryName,
    icon = categoryIcon
)

fun TransactionType.toName(): StringResource = when(this) {
    TransactionType.Income -> Res.string.income
    TransactionType.Expense -> Res.string.expenses
}

fun filterSuggestedTitles(query: String, savedTitles: List<String>): List<String> {
    if (savedTitles.isEmpty()) return emptyList()
    val trimmed = query.trim()
    if (trimmed.isEmpty()) return savedTitles.take(5)

    return savedTitles
        .filter { it.contains(trimmed, ignoreCase = true) && !it.equals(trimmed, ignoreCase = true) }
        .sortedByDescending { it.startsWith(trimmed, ignoreCase = true) }
        .take(5)
}