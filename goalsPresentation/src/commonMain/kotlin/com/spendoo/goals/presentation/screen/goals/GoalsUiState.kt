package com.spendoo.goals.presentation.screen.goals

import com.spendoo.designsystem.components.cards.Priority
import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.entity.GoalsSummary
import com.spendoo.goals.presentation.screen.goals.components.AddAmountUiState
import com.spendoo.goals.presentation.screen.addEditGoal.AddEditGoalUiState

data class GoalsUiState(
    val isSummaryLoading: Boolean = false,
    val isGoalsLoading: Boolean = false,
    val isGoalsLoadingMore: Boolean = false,
    val summary: GoalsSummary = GoalsSummary(0.0, 0.0, 0.0),
    val goals: List<Goal> = emptyList(),
    val goalToEdit: Goal? = null,
    val isAddGoalBottomSheetVisible: Boolean = false,
    val isAddAmountToGoalVisible: Boolean = false,
    val isGoalActionsSheetVisible: Boolean = false,
    val addEditUiState: AddEditGoalUiState = AddEditGoalUiState(),
    val addAmountUiState: AddAmountUiState = AddAmountUiState(),
    val isAddAmountToSavingsVisible: Boolean = false
)

fun Int.toUiState(isCompleted: Boolean) = if (isCompleted) {
    Priority.Done
} else {
    when (this) {
        0 -> Priority.LOW
        1 -> Priority.MEDIUM
        2 -> Priority.HIGH
        else -> Priority.MEDIUM
    }
}