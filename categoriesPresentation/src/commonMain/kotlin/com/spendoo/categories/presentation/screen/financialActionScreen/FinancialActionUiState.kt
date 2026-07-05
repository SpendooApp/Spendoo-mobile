package com.spendoo.categories.presentation.screen.financialActionScreen

data class FinancialActionUiState(
    val tile: String = "",
    val body: String = "",
    val payload: Map<String, String> = emptyMap()
)