package com.spendoo.categories.presentation.screen.addCategoryBottomSheet

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.PriorityOption
import com.spendoo.categories.domain.entity.category.ResetCycleOption
import kotlinx.datetime.LocalDate

interface AddCategoryInteractionListener {
    fun onCategoryNameChanged(name: String)
    fun onBudgetChanged(budget: Double?)
    fun onBudgetStartDateChanged(date: LocalDate)
    fun onLeftoverFundsActionChanged(action: LeftOverOption)
    fun onPriorityChanged(priority: PriorityOption)
    fun onIconChanged(icon: CategoryIcon)
    fun onResetCycleChanged(cycle: ResetCycleOption)
    fun onShowDatePicker(show: Boolean)
    fun onShowLeftoverFundsActionSheet(show: Boolean)
    fun submit(onSuccess: (AddEditCategoryUiState) -> Unit)
}