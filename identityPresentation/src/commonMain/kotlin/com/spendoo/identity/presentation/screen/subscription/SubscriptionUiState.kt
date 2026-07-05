package com.spendoo.identity.presentation.screen.subscription

import com.spendoo.identity.domain.model.BillingCycle
import com.spendoo.identity.domain.model.SubscriptionPlan

data class SubscriptionUiState(
    val plans: List<SubscriptionPlan> = emptyList(),
    val selectedPlanId: String? = null,
    val selectedBillingCycle: BillingCycle = BillingCycle.YEARLY,
    val isLoading: Boolean = false,
    val isSubscribing: Boolean = false
) {
    val selectedPlan: SubscriptionPlan?
        get() = plans.find { it.id == selectedPlanId } ?: plans.find { it.isSelected } ?: plans.firstOrNull()
}
