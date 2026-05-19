package com.spendoo.categories.presentation.screen.categories

import androidx.lifecycle.viewModelScope
import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.utils.PageQuery
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.AddEditCategoryUiState
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.CategoryActionType
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toAddEditCategoryUiState
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toCreateCategory
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toUpdateCategory
import com.spendoo.categories.presentation.shared.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.category_id_is_missing
import spendoo.designsystem.generated.resources.error_adding_amount_to_category
import spendoo.designsystem.generated.resources.error_creating_category
import spendoo.designsystem.generated.resources.error_deleting_category
import spendoo.designsystem.generated.resources.error_loading_categories
import spendoo.designsystem.generated.resources.error_loading_summary
import spendoo.designsystem.generated.resources.error_updating_category

class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<CategoriesUiState>(CategoriesUiState()), CategoriesInteractionListener {

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
                        categories = it.categories + categories,
                        categoriesEndReached = categories.isEmpty() || categories.size < PAGE_SIZE
                    )
                }
            },
            onLoadUpdated = { isLoading ->
                updateState {
                    if (it.categories.isEmpty()) {
                        copy(isCategoriesLoading = isLoading)
                    } else {
                        copy(isCategoriesLoadingMore = isLoading, isCategoriesLoading = false)
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

    init {
        getData()
    }

    private fun getData() {
        getSummary()
        getCategories()
    }

    private fun getSummary() {
        tryToCall(
            onStart = { updateState { it.copy(isSummaryLoading = true) } },
            block = { categoriesRepository.getCategoriesSummary() },
            onSuccess = { summary -> updateState { it.copy(summary = summary.toCategorySummaryUiState()) } },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_loading_summary),
                    message = error.message?.let { error ->
                        UiText.DynamicString(error)
                    },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(isSummaryLoading = false) } }
        )
    }

    private fun getCategories() {
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

    override fun onAddEditCategory(addEditCategoryUiState: AddEditCategoryUiState) {
        if (addEditCategoryUiState.categoryId == null) {
            createCategory(addEditCategoryUiState)
        } else {
            editCategory(categoryId = addEditCategoryUiState.categoryId, addEditCategoryUiState)
        }
    }

    private fun createCategory(addEditCategoryUiState: AddEditCategoryUiState) {
        tryToCall(
            onStart = { updateState { it.copy(isAddEditCategoryLoading = true) } },
            block = { categoriesRepository.createCategory(addEditCategoryUiState.toCreateCategory()) },
            onSuccess = {
                getData()
                updateState { it.copy(isAddCategoryBottomSheetVisible = false) }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_creating_category),
                    message = error.message?.let { error ->
                        UiText.DynamicString(error)
                    },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(isAddEditCategoryLoading = false) } }
        )
    }

    private fun editCategory(categoryId: String, addEditCategoryUiState: AddEditCategoryUiState) {
        tryToCall(
            onStart = { updateState { it.copy(isAddEditCategoryLoading = true) } },
            block = {
                categoriesRepository.updateCategory(
                    categoryId = categoryId,
                    addEditCategoryUiState.toUpdateCategory()
                )
            },
            onSuccess = {
                getData()
                updateState { it.copy(isAddCategoryBottomSheetVisible = false) }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_updating_category),
                    message = error.message?.let { error ->
                        UiText.DynamicString(error)
                    },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(isAddEditCategoryLoading = false) } }
        )
    }

    override fun onAddCategoryClicked() {
        updateState { it.copy(isAddCategoryBottomSheetVisible = true, categoryToEdit = null) }
    }

    override fun onAddCategoryBottomSheetDismissed() {
        updateState { it.copy(isAddCategoryBottomSheetVisible = false, categoryToEdit = null) }
    }

    override fun setCategoryToEdit(category: Category) {
        updateState {
            it.copy(
                categoryToEdit = category.toAddEditCategoryUiState(),
                isCategoryActionsSheetVisible = true
            )
        }
    }

    override fun onCategoryActionSelected(categoryActionType: CategoryActionType?) {
        val category = state.value.categoryToEdit ?: return
        when (categoryActionType) {
            CategoryActionType.AddAmount -> {
                updateState {
                    it.copy(
                        isAddAmountToCategoryVisible = true,
                        isCategoryActionsSheetVisible = false
                    )
                }
            }

            CategoryActionType.Edit -> {
                updateState {
                    it.copy(
                        isAddCategoryBottomSheetVisible = true,
                        isCategoryActionsSheetVisible = false
                    )
                }
            }

            CategoryActionType.Delete -> {
                if (category.categoryId != null) {
                    deleteCategory(category.categoryId)
                }
            }

            null -> {
                updateState { it.copy(isCategoryActionsSheetVisible = false) }
            }
        }
    }

    private fun deleteCategory(categoryId: String) {
        tryToCall(
            block = { categoriesRepository.deleteCategory(categoryId) },
            onSuccess = {
                getData()
                updateState {
                    it.copy(
                        isCategoryActionsSheetVisible = false,
                        categoryToEdit = null
                    )
                }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_deleting_category),
                    message = error.message?.let { error ->
                        UiText.DynamicString(error)
                    },
                    isSuccess = false
                )
            }
        )
    }

    override fun onCategoryActionsSheetDismissed() {
        updateState { it.copy(categoryToEdit = null, isCategoryActionsSheetVisible = false) }
    }

    override fun onAddAmountToCategory(amount: Double) {
        val category = state.value.categoryToEdit ?: return
        if (category.categoryId == null) {
            showSnackBar(
                title = UiText.StringRes(Res.string.error_adding_amount_to_category),
                message = UiText.StringRes(Res.string.category_id_is_missing),
                isSuccess = false
            )
            return
        }
        val finalAmount = category.budget?.plus(amount) ?: amount
        val updatedCategory = category.copy(budget = finalAmount)

        tryToCall(
            onStart = { updateState { it.copy(isAddAmountToCategoryLoading = true) } },
            block = {
                categoriesRepository.updateCategory(
                    category.categoryId,
                    updatedCategory.toUpdateCategory()
                )
            },
            onSuccess = {
                getData()
                updateState { it.copy(isAddAmountToCategoryVisible = false, categoryToEdit = null) }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_adding_amount_to_category),
                    message = error.message?.let { error ->
                        UiText.DynamicString(error)
                    },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(isAddAmountToCategoryLoading = false) } }
        )
    }

    override fun onDismissAddAmountToCategory() {
        updateState { it.copy(isAddAmountToCategoryVisible = false, categoryToEdit = null) }
    }

    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_PAGE = 0
    }
}