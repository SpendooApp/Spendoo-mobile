package com.spendoo.goals.presentation.screen.addEditGoal

import com.spendoo.goals.domain.entity.GoalIcon
import com.spendoo.goals.domain.entity.PriorityOption
import kotlinx.datetime.LocalDate

interface AddGoalInteractionListener {
    fun onGoalNameChanged(name: String)
    fun onTargetAmountChanged(amount: Double?)
    fun onDeadlineChanged(date: LocalDate)
    fun onPriorityChanged(priority: PriorityOption)
    fun onIconChanged(icon: GoalIcon)
    fun onShowDatePicker(show: Boolean)
    fun submit(onSuccess: (AddEditGoalUiState) -> Unit)
}
