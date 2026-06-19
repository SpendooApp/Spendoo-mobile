package com.spendoo.goals.presentation.screen.addEditGoal

import com.spendoo.goals.domain.entity.GoalIcon
import com.spendoo.goals.domain.entity.PriorityOption
import kotlinx.datetime.LocalDate

data class AddEditGoalUiState(
    val goalId: String? = null,
    val goalName: String = "",
    val targetAmount: Double? = null,
    val deadline: LocalDate? = null,
    val priority: PriorityOption = PriorityOption.MEDIUM,
    val icon: GoalIcon = GoalIcon.DEFAULT,
    val showDatePicker: Boolean = false,
    val isLoading: Boolean = false
)
