package com.spendoo.categories.presentation.screen.scheduledPayments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.PriorityOption
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet.AddScheduledPaymentBottomSheet
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.badge.UpcomingBadge
import com.spendoo.designsystem.components.cards.MoneyCard
import com.spendoo.designsystem.components.cards.SubscriptionCard
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.asString
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_plus
import spendoo.designsystem.generated.resources.no_scheduled_payments_yet
import spendoo.designsystem.generated.resources.scheduled_payments
import spendoo.designsystem.generated.resources.total_budget

@Composable
fun ScheduledPaymentsScreen(
    viewModel: ScheduledPaymentsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ScheduledPaymentsContent(
        state = state,
        interactionListener = viewModel,
    )
}

@Composable
private fun ScheduledPaymentsContent(
    state: ScheduledPaymentsUiState,
    interactionListener: ScheduledPaymentsInteractionListener,
) {
    ScheduledPaymentsMainContent(
        state = state,
        interactionListener = interactionListener
    )

    AddScheduledPaymentBottomSheet(
        isVisible = state.isAddScheduledPaymentBottomSheetVisible,
        initialState = state.paymentToEdit?.toAddScheduledPaymentUiState(),
        onDismiss = interactionListener::onAddScheduledPaymentBottomSheetDismissed,
        onSuccess = {
            interactionListener.onAddScheduledPaymentBottomSheetDismissed()
            interactionListener.onReload()
        }
    )
}

@Composable
private fun ScheduledPaymentsMainContent(
    state: ScheduledPaymentsUiState,
    interactionListener: ScheduledPaymentsInteractionListener
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            onBackClicked = interactionListener::onBackClicked,
            title = stringResource(Res.string.scheduled_payments),
            actions = listOf(
                {
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_plus,
                        contentDescription = stringResource(Res.string.scheduled_payments),
                        onClick = { interactionListener.onAddScheduledPaymentClicked() }
                    )
                }
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            MoneyCard(
                modifier = Modifier.fillMaxWidth(),
                isLoading = state.isSummaryLoading,
                amount = state.summary.totalScheduledAmount.toInt().toString(),
                amountColor = Theme.colorScheme.brand.onPrimary,
                amountTextStyle = Theme.typography.heading.large,
                title = stringResource(Res.string.total_budget),
                titleColor = Theme.colorScheme.brand.primaryVariant,
                titleTextStyle = Theme.typography.body.small,
                backgroundColor = Theme.colorScheme.gradient.brand,
                moneyDataAlignment = Alignment.Start,
                trailingContent = {
                    UpcomingBadge(count = state.summary.upcomingCount)
                },
            )

            if (state.isScheduledPaymentsLoading) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(5) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .shimmerEffect()
                        )
                    }
                }
            } else if (state.scheduledPayments.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.no_scheduled_payments_yet),
                        style = Theme.typography.body.medium,
                        color = Theme.colorScheme.text.body,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.scheduledPayments) { payment ->
                        SubscriptionCard(
                            icon = payment.categoryIcon.toDrawableResource(),
                            title = payment.name,
                            amount = payment.amount,
                            timeLeft = payment.timeLeft.asString(),
                            isDueSoon = payment.isDueSoon,
                            onSkipClick = { interactionListener.onSkipPayment(payment.id) },
                            onPayClick = { interactionListener.onPayPayment(payment.id) },
                            onClick = { interactionListener.navigateToDetails(payment.id) }
                        )
                    }

                    if (state.isScheduledPaymentsLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(100.dp).navigationBarsPadding())
                    }
                }
            }
        }
        PaginationTrigger(
            list = state.scheduledPayments,
            listState = listState,
            remainingItemsToLoadNextPage = 5,
            loadNextItems = interactionListener::onListScrolled
        )
    }
}

@Composable
@Preview
fun ScheduledPaymentsScreenPreview() = SpendooTheme {
    ScheduledPaymentsMainContent(
        state = ScheduledPaymentsUiState(
            summary = ScheduledPaymentSummaryUiState(
                totalScheduledAmount = 1000.0,
            ),
            scheduledPayments = listOf(
                ScheduledPaymentUiState(
                    id = "1",
                    name = "Groceries",
                    categoryIcon = CategoryIcon.FOOD,
                    priority = PriorityOption.HIGH,
                    leftOverOption = LeftOverOption.RESET_TO_ORIGINAL_AMOUNT,
                    timeLeft = UiText.DynamicString("2 days left"),
                    isDueSoon = true
                ),
                ScheduledPaymentUiState(
                    id = "2",
                    name = "Rent",
                    categoryIcon = CategoryIcon.ENTERTAINMENT,
                    priority = PriorityOption.MEDIUM,
                    leftOverOption = LeftOverOption.MOVE_TO_SAVINGS,
                    timeLeft = UiText.DynamicString("Tomorrow"),
                    isDueSoon = true
                )
            ),
            isSummaryLoading = false,
            isScheduledPaymentsLoading = false,
            isScheduledPaymentsLoadingMore = false,
            isAddScheduledPaymentBottomSheetVisible = false,
            isScheduledPaymentActionsSheetVisible = false
        ),
        interactionListener = object : ScheduledPaymentsInteractionListener {
            override fun onReload() {}
            override fun onBackClicked() {}
            override fun onAddScheduledPaymentClicked() {}
            override fun onAddScheduledPaymentBottomSheetDismissed() {}
            override fun setScheduledPaymentToEdit(payment: ScheduledPaymentUiState) {}
            override fun onScheduledPaymentActionsSheetDismissed() {}
            override fun onListScrolled() {}
            override fun navigateToDetails(paymentId: String) {}
            override fun onSkipPayment(paymentId: String) {}
            override fun onPayPayment(paymentId: String) {}
        }
    )
}