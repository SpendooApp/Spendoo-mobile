package com.spendoo.categories.presentation.screen.financialActionScreen

import com.spendoo.categories.domain.repository.BudgetActionRepository
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.unknown_error

class FinancialActionViewModel(
    private val navTile: String,
    private val navBody: String,
    private val navActionId: String,
    private val budgetActionRepository: BudgetActionRepository
) : BaseViewModel<FinancialActionUiState>(FinancialActionUiState()),
    FinancialActionInteractionListener {

    init {
        updateState {
            copy(
                tile = navTile,
                body = navBody,
                actionId = navActionId
            )
        }
    }

    override fun onDismiss() {
        popBackStack()
    }

    override fun onExecute() {
        val actionId = state.value.actionId
        tryToCall(
            onStart = {
                updateState { copy(buttonState = AppButtonState.Loading) }
            },
            block = { budgetActionRepository.executeProposedAction(actionId) },
            onSuccess = {
                popBackStack()
            },
            onError = { throwable ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = throwable.message?.let { msg -> UiText.DynamicString(msg) }
                        ?: UiText.StringRes(Res.string.unknown_error),
                    isSuccess = false
                )
            },
            onEnd = {
                updateState { copy(buttonState = AppButtonState.Enabled) }
            }
        )
    }
}
