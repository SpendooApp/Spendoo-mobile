package com.spendoo.identity.presentation.screen.subscription

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.cards.SubscriptionBuyCard
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.domain.model.BillingCycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.cancel_anytime_secure_payment
import spendoo.designsystem.generated.resources.continue_button
import spendoo.designsystem.generated.resources.continue_with
import spendoo.designsystem.generated.resources.monthly
import spendoo.designsystem.generated.resources.save_20_percent
import spendoo.designsystem.generated.resources.unlock_spendoo_premium
import spendoo.designsystem.generated.resources.yearly

@Composable
fun SubscriptionScreen(
    viewModel: SubscriptionViewModel = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SubscriptionScreenContent(state = state, interactionListener = viewModel)
}

@Composable
private fun SubscriptionScreenContent(
    state: SubscriptionUiState,
    interactionListener: SubscriptionInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.unlock_spendoo_premium),
            onBackClicked = interactionListener::onBackClicked
        )

        Spacer(modifier = Modifier.height(8.dp))

        BillingCycleSwitcher(
            selectedCycle = state.selectedBillingCycle,
            onCycleSelected = interactionListener::onBillingCycleChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.plans, key = { it.id }) { plan ->
                    SubscriptionBuyCard(
                        title = plan.title,
                        description = plan.description,
                        subscriptionPrice = if (state.selectedBillingCycle == BillingCycle.MONTHLY) plan.priceMonthly else plan.priceMonthly,
                        yearlySubscriptionPrice = if (state.selectedBillingCycle == BillingCycle.YEARLY) plan.priceYearly else 0.0,
                        features = plan.benefits,
                        isSelected = plan.id == state.selectedPlanId,
                        isMostPopular = plan.isMostPopular,
                        modifier = Modifier.clickableNoRipple() {
                            interactionListener.onPlanSelected(plan.id)
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val selectedPlanTitle = state.selectedPlan?.title ?: ""
            AppButton(
                text = if (selectedPlanTitle.isNotEmpty()) {
                    stringResource(Res.string.continue_with, selectedPlanTitle)
                } else {
                    stringResource(Res.string.continue_button)
                },
                onClick = interactionListener::onContinueClicked,
                state = if (state.isSubscribing) AppButtonState.Loading else AppButtonState.Enabled,
                type = AppButtonType.Primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(Res.string.cancel_anytime_secure_payment),
                style = Theme.typography.label.medium.small.copy(fontSize = 12.sp),
                color = Theme.colorScheme.text.body
            )
        }
    }
}

@Composable
private fun BillingCycleSwitcher(
    selectedCycle: BillingCycle,
    onCycleSelected: (BillingCycle) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Theme.colorScheme.background.secondary)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (selectedCycle == BillingCycle.MONTHLY) Theme.colorScheme.button.secondary else Theme.colorScheme.background.secondary)
                .clickableNoRipple { onCycleSelected(BillingCycle.MONTHLY) }
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.monthly),
                style = Theme.typography.label.medium.medium,
                color = if (selectedCycle == BillingCycle.MONTHLY) Theme.colorScheme.text.title else Theme.colorScheme.text.body
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (selectedCycle == BillingCycle.YEARLY) Theme.colorScheme.button.secondary else Theme.colorScheme.background.secondary)
                .clickableNoRipple { onCycleSelected(BillingCycle.YEARLY) }
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(Res.string.yearly),
                    style = Theme.typography.label.medium.medium,
                    color = if (selectedCycle == BillingCycle.YEARLY) Theme.colorScheme.text.title else Theme.colorScheme.text.body
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Theme.colorScheme.button.primary.copy(alpha = 0.2f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.save_20_percent),
                        style = Theme.typography.label.medium.extraSmall.copy(fontSize = 10.sp),
                        color = Theme.colorScheme.button.primary
                    )
                }
            }
        }
    }
}

