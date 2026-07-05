package com.spendoo.identity.presentation.screen.subscription

import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.model.BillingCycle
import com.spendoo.identity.domain.repository.SubscriptionRepository
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred

class SubscriptionViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel<SubscriptionUiState>(SubscriptionUiState()),
    SubscriptionInteractionListener {

    init {
        loadSubscriptionPlans()
    }

    private fun loadSubscriptionPlans() {
        updateState { it.copy(isLoading = true) }
        tryToCall(
            block = { subscriptionRepository.getSubscriptionPlans() },
            onSuccess = { plans ->
                val defaultSelectedId = plans.find { it.isSelected }?.id ?: plans.firstOrNull()?.id
                updateState {
                    it.copy(
                        plans = plans,
                        selectedPlanId = defaultSelectedId,
                        isLoading = false
                    )
                }
            },
            onError = { throwable ->
                updateState { it.copy(isLoading = false) }
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    override fun onBillingCycleChanged(cycle: BillingCycle) {
        updateState { it.copy(selectedBillingCycle = cycle) }
    }

    override fun onPlanSelected(planId: String) {
        updateState { it.copy(selectedPlanId = planId) }
    }

    override fun onContinueClicked() {
        val planId = state.value.selectedPlanId ?: return
        val billingCycle = state.value.selectedBillingCycle

        updateState { it.copy(isSubscribing = true) }
        tryToCall(
            block = { subscriptionRepository.subscribePlan(planId, billingCycle) },
            onSuccess = {
                updateState { it.copy(isSubscribing = false) }
                popBackStack()
            },
            onError = { throwable ->
                updateState { it.copy(isSubscribing = false) }
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    override fun onBackClicked() {
        popBackStack()
    }
}

