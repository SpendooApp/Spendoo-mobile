package com.spendoo.categories.presentation.screen.addCategoryBottomSheet

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.shared.domain.entity.PriorityOption
import com.spendoo.categories.domain.entity.category.ResetCycleOption
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.error_creating_category
import spendoo.designsystem.generated.resources.error_updating_category

class AddCategoryViewModel(
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<AddEditCategoryUiState>(AddEditCategoryUiState())
, AddCategoryInteractionListener {

    fun init(initialState: AddEditCategoryUiState?) {
        updateState { initialState ?: AddEditCategoryUiState() }
    }

    override fun onCategoryNameChanged(name: String) {
        updateState { it.copy(categoryName = name) }
    }

    override fun onBudgetChanged(budget: Double?) {
        updateState { it.copy(budget = budget) }
    }

    override fun onBudgetStartDateChanged(date: LocalDate) {
        updateState { it.copy(budgetStartDate = date, showDatePicker = false) }
    }

    override fun onLeftoverFundsActionChanged(action: LeftOverOption) {
        updateState { it.copy(leftoverFundsAction = action, showLeftoverFundsActionSheet = false) }
    }

    override fun onPriorityChanged(priority: PriorityOption) {
        updateState { it.copy(priority = priority) }
    }

    override fun onIconChanged(icon: CategoryIcon) {
        updateState { it.copy(icon = icon) }
    }

    override fun onResetCycleChanged(cycle: ResetCycleOption) {
        updateState { it.copy(resetCycle = cycle) }
    }

    override fun onShowDatePicker(show: Boolean) {
        updateState { it.copy(showDatePicker = show) }
    }

    override fun onShowLeftoverFundsActionSheet(show: Boolean) {
        updateState { it.copy(showLeftoverFundsActionSheet = show) }
    }

    override fun submit(onSuccess: (AddEditCategoryUiState) -> Unit) {
        val currentState = state.value
        tryToCall(
            onStart = { updateState { it.copy(isLoading = true) } },
            block = {
                if (currentState.categoryId == null) {
                    categoriesRepository.createCategory(currentState.toCreateCategory())
                } else {
                    categoriesRepository.updateCategory(
                        currentState.categoryId,
                        currentState.toUpdateCategory()
                    )
                }
            },
            onSuccess = {
                onSuccess(currentState)
            },
            onError = { throwable ->
                showSnackBar(
                    title = when (state.value.categoryId) {
                        null -> UiText.StringRes(Res.string.error_creating_category)
                        else -> UiText.StringRes(Res.string.error_updating_category)
                    },
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(isLoading = false) } }
        )
    }
}
