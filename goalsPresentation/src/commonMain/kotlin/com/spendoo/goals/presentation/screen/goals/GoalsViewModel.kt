package com.spendoo.goals.presentation.screen.goals

import androidx.lifecycle.viewModelScope
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.entity.PriorityOption
import com.spendoo.goals.domain.repository.GoalsRepository
import com.spendoo.goals.domain.utils.PageQuery
import com.spendoo.goals.presentation.screen.goals.components.AddAmountUiState
import com.spendoo.goals.presentation.screen.addEditGoal.AddEditGoalUiState
import com.spendoo.goals.presentation.screen.goals.components.GoalActionType
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.error_loading_goals
import spendoo.designsystem.generated.resources.error_loading_summary
import spendoo.designsystem.generated.resources.error_deleting_goal
import spendoo.designsystem.generated.resources.error_adding_amount_to_goal
import spendoo.designsystem.generated.resources.error_adding_to_saving

class GoalsViewModel(
    private val goalsRepository: GoalsRepository
) : BaseViewModel<GoalsUiState>(GoalsUiState()), GoalsInteractionListener {

    private val goalsPaginator by lazy {
        createPaginator(
            initialKey = INITIAL_PAGE,
            pageSize = PAGE_SIZE,
            loadPage = { pageNumber ->
                goalsRepository.getGoals(PageQuery(pageNumber, PAGE_SIZE)).data
            },
            onSuccess = { goals ->
                updateState {
                    copy(
                        goals = it.goals + goals,
                        isGoalsLoadingMore = false,
                        isGoalsLoading = false
                    )
                }
            },
            onLoadUpdated = { isLoading ->
                updateState {
                    if (it.goals.isEmpty()) {
                        copy(isGoalsLoading = isLoading)
                    } else {
                        copy(isGoalsLoadingMore = isLoading, isGoalsLoading = false)
                    }
                }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_loading_goals),
                    message = error?.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    init {
        getData()
    }

    private fun getData() {
        getSummary()
        getGoals()
    }

    private fun getSummary() {
        tryToCall(
            onStart = { updateState { it.copy(isSummaryLoading = true) } },
            block = { goalsRepository.getGoalsSummary() },
            onSuccess = { summary -> updateState { it.copy(summary = summary) } },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_loading_summary),
                    message = error.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(isSummaryLoading = false) } }
        )
    }

    private fun getGoals() {
        viewModelScope.launch {
            updateState { it.copy(goals = emptyList()) }
            goalsPaginator.reset()
            goalsPaginator.loadNextItems()
        }
    }

    override fun onReload() {
        getData()
    }

    override fun onListScrolled() {
        viewModelScope.launch {
            goalsPaginator.loadNextItems()
        }
    }

    override fun onBackClicked() {
        popBackStack()
    }

    override fun onAddGoalClicked() {
        updateState {
            it.copy(
                isAddGoalBottomSheetVisible = true,
                goalToEdit = null,
                addEditUiState = AddEditGoalUiState()
            )
        }
    }

    override fun onAddGoalBottomSheetDismissed() {
        updateState { it.copy(isAddGoalBottomSheetVisible = false, goalToEdit = null) }
    }

    override fun onGoalClickMenu(goal: Goal) {
        updateState {
            it.copy(
                goalToEdit = goal,
                isGoalActionsSheetVisible = true
            )
        }
    }

    override fun onGoalActionSelected(action: GoalActionType?) {
        val goal = state.value.goalToEdit ?: return
        when (action) {
            GoalActionType.AddAmount -> {
                updateState {
                    it.copy(
                        isAddAmountToGoalVisible = true,
                        isGoalActionsSheetVisible = false,
                        addAmountUiState = AddAmountUiState()
                    )
                }
            }
            GoalActionType.Edit -> {
                val editState = AddEditGoalUiState(
                    goalId = goal.id,
                    goalName = goal.name,
                    targetAmount = goal.targetAmount,
                    deadline = goal.deadline.date,
                    priority = goal.priority.toPriorityOption(),
                    icon = goal.icon
                )
                updateState {
                    it.copy(
                        isAddGoalBottomSheetVisible = true,
                        isGoalActionsSheetVisible = false,
                        addEditUiState = editState
                    )
                }
            }
            GoalActionType.Delete -> {
                deleteGoal(goal.id)
            }
            null -> {
                updateState { it.copy(isGoalActionsSheetVisible = false) }
            }
        }
    }

    private fun deleteGoal(goalId: String) {
        tryToCall(
            block = { goalsRepository.deleteGoal(goalId) },
            onSuccess = {
                getData()
                updateState {
                    it.copy(
                        isGoalActionsSheetVisible = false,
                        goalToEdit = null
                    )
                }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_deleting_goal),
                    message = error.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    override fun onGoalActionsSheetDismissed() {
        updateState { it.copy(goalToEdit = null, isGoalActionsSheetVisible = false) }
    }

    override fun onDismissAddAmount() {
        updateState { it.copy(isAddAmountToGoalVisible = false, goalToEdit = null) }
    }

    override fun onAddAmount(amount: Double) {
        val goal = state.value.goalToEdit ?: return
        tryToCall(
            onStart = { updateState { it.copy(addAmountUiState = it.addAmountUiState.copy(isLoading = true)) } },
            block = {
                goalsRepository.assignAmount(goal.id, amount)
            },
            onSuccess = {
                getData()
                updateState {
                    it.copy(
                        isAddAmountToGoalVisible = false,
                        goalToEdit = null
                    )
                }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_adding_amount_to_goal),
                    message = error.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(addAmountUiState = it.addAmountUiState.copy(isLoading = false)) } }
        )
    }

    override fun onAddAmountToSavingsClicked() {
        updateState {
            it.copy(
                isAddAmountToSavingsVisible = true,
                addAmountUiState = AddAmountUiState()
            )
        }
    }

    override fun onDismissAddAmountToSavings() {
        updateState { it.copy(isAddAmountToSavingsVisible = false) }
    }

    override fun onAddAmountToSavings(amount: Double) {
        tryToCall(
            onStart = { updateState { it.copy(addAmountUiState = it.addAmountUiState.copy(isLoading = true)) } },
            block = {
                goalsRepository.addToSavings(amount)
            },
            onSuccess = {
                getData()
                updateState {
                    it.copy(
                        isAddAmountToSavingsVisible = false
                    )
                }
            },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.error_adding_to_saving),
                    message = error.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            },
            onEnd = { updateState { it.copy(addAmountUiState = it.addAmountUiState.copy(isLoading = false)) } }
        )
    }

    override fun onAddEditGoal(addEditUiState: AddEditGoalUiState) {
        getData()
        updateState { it.copy(isAddGoalBottomSheetVisible = false, goalToEdit = null) }
    }

    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_PAGE = 0
    }
}
