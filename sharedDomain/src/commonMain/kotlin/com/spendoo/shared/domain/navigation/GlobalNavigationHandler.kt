package com.spendoo.shared.domain.navigation

interface GlobalNavigationHandler {
    suspend fun onPaymentRequiredError()
}
