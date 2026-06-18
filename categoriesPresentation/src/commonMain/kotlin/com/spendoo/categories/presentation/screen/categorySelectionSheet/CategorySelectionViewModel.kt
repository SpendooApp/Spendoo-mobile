package com.spendoo.categories.presentation.screen.categorySelectionSheet

import androidx.lifecycle.viewModelScope
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.utils.PageQuery
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.toCategoryItemUiState
import com.spendoo.categories.presentation.screen.categories.CategoriesViewModel.Companion.INITIAL_PAGE
import com.spendoo.categories.presentation.screen.categories.CategoriesViewModel.Companion.PAGE_SIZE
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.error_loading_categories

class CategorySelectionViewModel(
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<CategorySelectionUiState>(CategorySelectionUiState()), CategorySelectionInteractionListener {

    private val categoriesPaginator by lazy {
        createPaginator(
            initialKey = INITIAL_PAGE,
            pageSize = PAGE_SIZE,
            loadPage = { pageNumber ->
                categoriesRepository.getCategories(PageQuery(pageNumber, PAGE_SIZE)).data
            },
            onSuccess = { categories ->
                updateState {
                    copy(
                        categories = it.categories + categories.map { category -> category.toCategoryItemUiState() },
                        categoriesEndReached = categories.isEmpty() || categories.size < PAGE_SIZE
                    )
                }
            },
            onLoadUpdated = { isLoading ->
                updateState {
                    if (it.categories.isEmpty()) {
                        copy(isLoading = isLoading)
                    } else {
                        copy(isCategoriesLoadingMore = isLoading, isLoading = false)
                    }
                }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_loading_categories),
                    message = error?.message?.let { error ->
                        UiText.DynamicString(error)
                    },
                    isSuccess = false
                )
            },
        )
    }

    override fun onCategorySelected(category: CategoryItemUiState) {
        updateState { it.copy(selectedCategory = category) }
    }

    override fun loadCategories() {
        viewModelScope.launch {
            updateState { it.copy(categories = emptyList(), categoriesEndReached = false) }
            categoriesPaginator.reset()
            categoriesPaginator.loadNextItems()
        }
    }

    override fun onListScrolled() {
        viewModelScope.launch {
            if (!state.value.categoriesEndReached) {
                categoriesPaginator.loadNextItems()
            }
        }
    }

    override fun showAddCategorySheet(bool: Boolean) {
        updateState { it.copy(showAddCategorySheet = bool) }
    }
}
