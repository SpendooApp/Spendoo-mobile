package com.spendoo.categories.presentation.screen.topSpendingCategories

import androidx.lifecycle.viewModelScope
import com.spendoo.categories.api.CategoryOffersRoute
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.shared.domain.utils.PageQuery
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred

class TopSpendingCategoriesViewModel(
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<TopSpendingCategoriesUiState>(TopSpendingCategoriesUiState()), 
    TopSpendingCategoriesInteractionListener {

    private val paginator by lazy {
        createPaginator(
            initialKey = 0,
            pageSize = 20,
            loadPage = { pageNumber ->
                categoriesRepository.getTopSpending(PageQuery(pageNumber, 20)).data
            },
            onSuccess = { items ->
                updateState {
                    it.copy(
                        categories = it.categories + items
                    )
                }
            },
            onLoadUpdated = { isLoading ->
                updateState {
                    if (isLoading) {
                        if (it.categories.isEmpty()) {
                            it.copy(isLoading = true)
                        } else {
                            it.copy(isLoadingMore = true)
                        }
                    } else {
                        it.copy(isLoading = false, isLoadingMore = false)
                    }
                }
            },
            onError = { throwable ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = throwable?.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    init {
        loadData()
    }

    private fun loadData() {
        updateState { it.copy(categories = emptyList()) }
        viewModelScope.launch {
            paginator.reset()
            paginator.loadNextItems()
        }
    }

    override fun onBackClicked() {
        popBackStack()
    }

    override fun onCategoryClicked(categoryId: String) {
        navigate(CategoryOffersRoute(categoryId))
    }

    override fun onReload() {
        updateState { it.copy(isRefreshing = true) }
        loadData()
        updateState { it.copy(isRefreshing = false) }
    }

    override fun onListScrolled() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}
