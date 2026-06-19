package com.spendoo.goals.presentation.screen.addEditGoal

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.entity.PriorityOption
import kotlinx.datetime.LocalDate

data class AddEditGoalUiState(
    val goalId: String? = null,
    val goalName: String = "",
    val targetAmount: Double? = null,
    val deadline: LocalDate? = null,
    val priority: PriorityOption = PriorityOption.MEDIUM,
    val icon: CategoryIcon = CategoryIcon.DEFAULT,
    val showDatePicker: Boolean = false,
    val isLoading: Boolean = false
)
