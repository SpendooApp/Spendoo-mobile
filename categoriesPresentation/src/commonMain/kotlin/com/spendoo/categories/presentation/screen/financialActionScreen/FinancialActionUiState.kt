package com.spendoo.categories.presentation.screen.financialActionScreen

import com.spendoo.designsystem.components.button.AppButtonState

data class FinancialActionUiState(
    val tile: String = "",
    val body: String = "",
    val payload: Map<String, String> = emptyMap(),
    val buttonState: AppButtonState = AppButtonState.Enabled
)