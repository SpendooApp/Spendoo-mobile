package com.spendoo.navigation

import com.spendoo.designsystem.navigation.effector.Effector
import com.spendoo.identity.api.SubscriptionRoute
import com.spendoo.shared.domain.navigation.GlobalNavigationHandler

class GlobalNavigationHandlerImpl(
    private val effector: Effector
) : GlobalNavigationHandler {
    override suspend fun onPaymentRequiredError() {
        effector.navigate(SubscriptionRoute, forceNavigate = true)
    }
}