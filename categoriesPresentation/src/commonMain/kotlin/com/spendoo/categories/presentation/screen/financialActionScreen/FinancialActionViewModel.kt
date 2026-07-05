package com.spendoo.categories.presentation.screen.financialActionScreen

import com.spendoo.designsystem.navigation.BaseViewModel

class FinancialActionViewModel(
    private val navTile: String,
    private val navBody: String,
    private val navPayload: Map<String, String>
) : BaseViewModel<FinancialActionUiState>(FinancialActionUiState()),
    FinancialActionInteractionListener {

    init {
        updateState {
            copy(
                tile = navTile,
                body = navBody,
                payload = navPayload
            )
        }
    }

    override fun onDismiss() {
        popBackStack()
    }
}
