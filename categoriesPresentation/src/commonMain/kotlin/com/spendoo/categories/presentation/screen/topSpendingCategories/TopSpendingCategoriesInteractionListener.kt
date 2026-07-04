package com.spendoo.categories.presentation.screen.topSpendingCategories

interface TopSpendingCategoriesInteractionListener {
    fun onBackClicked()
    fun onCategoryClicked(categoryId: String)
    fun onReload()
    fun onListScrolled()
}
