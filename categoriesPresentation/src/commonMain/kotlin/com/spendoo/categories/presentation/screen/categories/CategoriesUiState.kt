package com.spendoo.categories.presentation.screen.categories

import com.spendoo.categories.domain.entity.category.Budget
import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.categories.domain.entity.category.CategorySummary
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.AddEditCategoryUiState
import com.spendoo.designsystem.components.cards.BudgetDataUiState

data class CategoriesUiState (
    val isRefreshing: Boolean = false,
    val summary: CategorySummaryUiState = CategorySummaryUiState(),
    val isSummaryLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val categoriesEndReached: Boolean = false,
    val isCategoriesLoading: Boolean = false,
    val isCategoriesLoadingMore: Boolean = false,
    val categoryToEdit: AddEditCategoryUiState? = null,
    val isAddCategoryBottomSheetVisible: Boolean = false,
    val isCategoryActionsSheetVisible: Boolean = false,
    val isAddAmountToCategoryVisible: Boolean = false,
    val isAddAmountToCategoryLoading: Boolean = false
)

data class CategorySummaryUiState(
    val totalBudget: Double = 0.0,
    val totalSpent: Double = 0.0,
    val addedIncome: Double = 0.0,
)

fun CategorySummary.toCategorySummaryUiState(): CategorySummaryUiState {
    return CategorySummaryUiState(
        totalBudget = this.totalBudget,
        totalSpent = this.totalSpent,
        addedIncome = this.addedIncome
    )
}

fun Budget.toBudgetDataUiState(): BudgetDataUiState {
    return BudgetDataUiState(
        startDate = this.startDate.date,
        endDate = this.endDate.date,
        percentage = this.spendingPercentage,
        total = this.amount.toInt()
    )
}
