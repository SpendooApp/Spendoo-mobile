package com.spendoo.identity.presentation.screen.subscription

import com.spendoo.identity.domain.model.BillingCycle

interface SubscriptionInteractionListener {
    fun onBackClicked()
    fun onBillingCycleChanged(cycle: BillingCycle)
    fun onPlanSelected(planId: String)
    fun onContinueClicked()
}
