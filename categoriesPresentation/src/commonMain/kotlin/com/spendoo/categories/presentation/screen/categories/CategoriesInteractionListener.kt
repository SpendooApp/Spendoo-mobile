package com.spendoo.categories.presentation.screen.categories

import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.AddEditCategoryUiState
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.CategoryActionType

interface CategoriesInteractionListener {
    fun onAddEditCategory(addEditCategoryUiState: AddEditCategoryUiState)
    fun onAddCategoryClicked()
    fun onAddCategoryBottomSheetDismissed()
    fun setCategoryToEdit(category: Category)
    fun onCategoryActionSelected(categoryActionType: CategoryActionType?)
    fun onCategoryActionsSheetDismissed()
    fun onAddAmountToCategory(amount: Double)
    fun onDismissAddAmountToCategory()
    fun onListScrolled()
}