package com.spendoo.goals.presentation.screen.addEditGoal

import com.spendoo.goals.domain.repository.GoalsRepository
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.entity.PriorityOption
import com.spendoo.shared.domain.utils.toLocalDateTime
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.error_creating_goal
import spendoo.designsystem.generated.resources.error_updating_goal

class AddGoalViewModel(
    private val goalsRepository: GoalsRepository
) : BaseViewModel<AddEditGoalUiState>(AddEditGoalUiState()), AddGoalInteractionListener {

    fun init(initialState: AddEditGoalUiState?) {
        updateState { initialState ?: AddEditGoalUiState() }
    }

    override fun onGoalNameChanged(name: String) {
        updateState { it.copy(goalName = name) }
    }

    override fun onTargetAmountChanged(amount: Double?) {
        updateState { it.copy(targetAmount = amount) }
    }

    override fun onDeadlineChanged(date: LocalDate) {
        updateState { it.copy(deadline = date, showDatePicker = false) }
    }

    override fun onPriorityChanged(priority: PriorityOption) {
        updateState { it.copy(priority = priority) }
    }

    override fun onIconChanged(icon: CategoryIcon) {
        updateState { it.copy(icon = icon) }
    }

    override fun onShowDatePicker(show: Boolean) {
        updateState { it.copy(showDatePicker = show) }
    }

    override fun submit(onSuccess: (AddEditGoalUiState) -> Unit) {
        val currentState = state.value
        val targetAmount = currentState.targetAmount ?: 0.0
        val deadlineDate = currentState.deadline ?: return

        val priorityValue = when (currentState.priority) {
            PriorityOption.LOW -> 0
            PriorityOption.MEDIUM -> 1
            PriorityOption.HIGH -> 2
        }

        tryToCall(
            onStart = { updateState { it.copy(isLoading = true) } },
            block = {
                val deadlineLdt = deadlineDate.toLocalDateTime()
                if (currentState.goalId == null) {
                    goalsRepository.createGoal(
                        name = currentState.goalName,
                        targetAmount = targetAmount,
                        deadline = deadlineLdt,
                        icon = currentState.icon,
                        priority = priorityValue
                    )
                } else {
                    goalsRepository.updateGoal(
                        goalId = currentState.goalId,
                        name = currentState.goalName,
                        targetAmount = targetAmount,
                        deadline = deadlineLdt,
                        icon = currentState.icon,
                        priority = priorityValue
                    )
                }
            },
            onSuccess = {
                onSuccess(currentState)
            },
            onError = { throwable ->
                showSnackBar(
                    title = when (state.value.goalId) {
                        null -> UiText.StringRes(Res.string.error_creating_goal)
                        else -> UiText.StringRes(Res.string.error_updating_goal)
                    },
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(isLoading = false) } }
        )
    }
}
