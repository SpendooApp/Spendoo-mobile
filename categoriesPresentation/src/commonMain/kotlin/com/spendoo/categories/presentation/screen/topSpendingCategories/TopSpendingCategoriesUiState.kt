package com.spendoo.categories.presentation.screen.topSpendingCategories

import com.spendoo.categories.domain.entity.transaction.CategorySpending

data class TopSpendingCategoriesUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val categories: List<CategorySpending> = emptyList(),
)
