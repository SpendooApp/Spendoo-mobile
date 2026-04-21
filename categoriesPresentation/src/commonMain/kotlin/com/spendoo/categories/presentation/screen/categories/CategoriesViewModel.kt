package com.spendoo.categories.presentation.screen.categories

import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.presentation.shared.BaseViewModel

class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<CategoriesUiState>(CategoriesUiState()), CategoriesInteractionListener {

}