package com.spendoo.categories.presentation.screen.categoryOffers

import com.spendoo.designsystem.navigation.BaseViewModel

class CategoryOffersViewModel(
    private val categoryId: String
) : BaseViewModel<CategoryOffersUiState>(CategoryOffersUiState(categoryId = categoryId)), 
    CategoryOffersInteractionListener {

    override fun onBackClicked() {
        popBackStack()
    }
}
