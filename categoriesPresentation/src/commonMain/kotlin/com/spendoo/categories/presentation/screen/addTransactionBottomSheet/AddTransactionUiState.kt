package com.spendoo.categories.presentation.screen.addTransactionBottomSheet

import androidx.compose.runtime.Composable
import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.presentation.shared.getToday
import com.spendoo.designsystem.components.general.GenSelectableOption
import com.spendoo.designsystem.utils.extentions.asString
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.enter_a_title
import spendoo.designsystem.generated.resources.enter_your_amount
import spendoo.designsystem.generated.resources.expenses
import spendoo.designsystem.generated.resources.income
import spendoo.designsystem.generated.resources.title_too_short
import spendoo.designsystem.generated.resources.title_too_long
import spendoo.designsystem.generated.resources.enter_a_valid_amount
import spendoo.designsystem.generated.resources.note_too_long
import spendoo.designsystem.generated.resources.please_select_a_category

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
){
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

    val titleError: String?
        @Composable
        get() = when {
            title.isBlank() -> Res.string.enter_a_title.asString()
            title.length < 2 -> Res.string.title_too_short.asString()
            title.length > 50 -> Res.string.title_too_long.asString()
            else -> null
        }

    val amountError: String?
        @Composable
        get() = when {
            amount == null -> Res.string.enter_your_amount.asString()
            amount <= 0 -> Res.string.enter_a_valid_amount.asString()
            else -> null
        }

    val noteError: String?
        @Composable
        get() = when {
            note.length > 200 -> Res.string.note_too_long.asString()
            else -> null
        }

    val categoryError: String?
        @Composable
        get() = if (!hasCategory) Res.string.please_select_a_category.asString() else null
}

data class AddTransactionUiState(
    val type: TransactionType = TransactionType.Expense,
    val expenseEntries: List<TransactionEntryUiState> = listOf(TransactionEntryUiState()),
    val incomeTitle: String = "",
    val incomeAmount: Double? = null,
    val incomeNote: String = "",
    val date: LocalDate = getToday(),
    val isSavingChecked: Boolean = false,
    val savingAmount: Double? = null,
    val showDatePicker: Boolean = false,
    val isSubmitting: Boolean = false,
    val isProcessingMedia: Boolean = false,
    val showCategorySheet: Boolean = false,
    val categories: List<CategoryItemUiState> = emptyList(),
    val showAudioPicker: Boolean = false,
    val selectedEntryIdForCategory: String? = null,
    val transactionTypeOptions: List<GenSelectableOption<TransactionType>> = listOf(
        GenSelectableOption(TransactionType.Expense, Res.string.expenses),
        GenSelectableOption(TransactionType.Income, Res.string.income),
    ),
) {
    val isSavingAmountValid: Boolean
        get() = !isSavingChecked || (savingAmount != null && savingAmount > 0)

    val isIncomeTitleValid: Boolean
        get() = incomeTitle.isNotBlank() && incomeTitle.length in 2..50

    val isIncomeAmountValid: Boolean
        get() = incomeAmount != null && incomeAmount > 0

    val isIncomeNoteValid: Boolean
        get() = incomeNote.length <= 200

    val incomeTitleError: String?
        @Composable
        get() = when {
            incomeTitle.isBlank() -> Res.string.enter_a_title.asString()
            incomeTitle.length < 2 -> Res.string.title_too_short.asString()
            incomeTitle.length > 50 -> Res.string.title_too_long.asString()
            else -> null
        }

    val incomeAmountError: String?
        @Composable
        get() = when {
            incomeAmount == null -> Res.string.enter_your_amount.asString()
            incomeAmount <= 0 -> Res.string.enter_a_valid_amount.asString()
            else -> null
        }

    val incomeNoteError: String?
        @Composable
        get() = when {
            incomeNote.length > 200 -> Res.string.note_too_long.asString()
            else -> null
        }

    val savingAmountError: String?
        @Composable
        get() = if (isSavingChecked) when {
            savingAmount == null -> Res.string.enter_your_amount.asString()
            savingAmount <= 0 -> Res.string.enter_a_valid_amount.asString()
            else -> null
        } else null

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
