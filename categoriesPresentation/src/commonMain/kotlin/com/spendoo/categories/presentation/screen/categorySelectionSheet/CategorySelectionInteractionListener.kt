package com.spendoo.categories.presentation.screen.categorySelectionSheet

import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState

interface CategorySelectionInteractionListener {
    fun onCategorySelected(category: CategoryItemUiState)
    fun loadCategories()
    fun showAddCategorySheet(bool: Boolean)
    fun onListScrolled()
}