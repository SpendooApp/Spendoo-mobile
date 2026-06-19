package com.spendoo.goals.presentation.screen.goals

import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.presentation.screen.addEditGoal.AddEditGoalUiState
import com.spendoo.goals.presentation.screen.goals.components.GoalActionType

interface GoalsInteractionListener {
    fun onReload()
    fun onBackClicked()
    fun onAddGoalClicked()
    fun onAddGoalBottomSheetDismissed()
    fun onGoalClickMenu(goal: Goal)
    fun onGoalActionSelected(action: GoalActionType?)
    fun onGoalActionsSheetDismissed()
    
    fun onDismissAddAmount()
    fun onAddAmount(amount: Double)
    
    fun onAddAmountToSavingsClicked()
    fun onDismissAddAmountToSavings()
    fun onAddAmountToSavings(amount: Double)
    
    fun onListScrolled()
    fun onAddEditGoal(addEditUiState: AddEditGoalUiState)
}
