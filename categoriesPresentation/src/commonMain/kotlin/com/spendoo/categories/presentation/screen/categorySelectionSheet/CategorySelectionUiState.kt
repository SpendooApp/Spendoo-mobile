package com.spendoo.categories.presentation.screen.categorySelectionSheet

import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState

data class CategorySelectionUiState(
    val categories: List<CategoryItemUiState> = emptyList(),
    val categoriesEndReached: Boolean = false,
    val isCategoriesLoadingMore: Boolean = false,
    val isLoading: Boolean = false,
    val selectedCategory: CategoryItemUiState? = null,
    val showAddCategorySheet: Boolean = false
)