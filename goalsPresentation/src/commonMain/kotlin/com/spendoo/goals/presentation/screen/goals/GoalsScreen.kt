package com.spendoo.goals.presentation.screen.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.utils.asString
import com.spendoo.goals.presentation.screen.goals.components.AddAmountToGoalBottomSheet
import com.spendoo.goals.presentation.screen.addEditGoal.AddEditGoalBottomSheet
import com.spendoo.goals.presentation.screen.goals.components.GoalActionsSheet
import com.spendoo.goals.presentation.screen.goals.components.toDrawableResource
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.cards.GoalCard
import com.spendoo.designsystem.components.cards.GoalDataUiState
import com.spendoo.designsystem.components.cards.MoneyCard
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.color.scheme.toBrush
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.modifier.clickableNoRipple
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.my_goals
import spendoo.designsystem.generated.resources.ic_plus
import spendoo.designsystem.generated.resources.no_goals_yet
import spendoo.designsystem.generated.resources.total_target
import spendoo.designsystem.generated.resources.total_saved
import spendoo.designsystem.generated.resources.unassigned_savings
import spendoo.designsystem.generated.resources.history

@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PullToRefresh(
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::onReload
    ) {
        GoalsContent(
            state = state,
            listener = viewModel
        )
    }
}

@Composable
private fun GoalsContent(
    state: GoalsUiState,
    listener: GoalsInteractionListener
) {
    val listState = rememberLazyListState()
    val firstCompletedIndex = state.goals.indexOfFirst { it.isCompleted }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.my_goals),
            actions = listOf(
                {
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_plus,
                        contentDescription = stringResource(Res.string.my_goals),
                        onClick = { listener.onAddGoalClicked() }
                    )
                }
            ),
            onBackClicked = listener::onBackClicked
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MoneyCard(
                        modifier = Modifier.fillMaxWidth(),
                        isLoading = state.isSummaryLoading,
                        amount = state.summary.totalSaved.toInt().toString(),
                        amountColor = Theme.colorScheme.brand.onPrimary,
                        amountTextStyle = Theme.typography.heading.large,
                        title = stringResource(Res.string.total_saved),
                        titleColor = Theme.colorScheme.brand.primaryVariant,
                        titleTextStyle = Theme.typography.body.small,
                        backgroundColor = Theme.colorScheme.gradient.brand,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MoneyCard(
                            modifier = Modifier.weight(1f),
                            isLoading = state.isSummaryLoading,
                            amount = state.summary.totalTarget.toInt().toString(),
                            amountColor = Theme.colorScheme.brand.onSecondary,
                            amountTextStyle = Theme.typography.heading.small,
                            title = stringResource(Res.string.total_target),
                            titleColor = Theme.colorScheme.text.body,
                            titleTextStyle = Theme.typography.label.medium.small,
                            backgroundColor = Theme.colorScheme.brand.secondary.toBrush(),
                            borderColor = Theme.colorScheme.border.primary
                        )
                        MoneyCard(
                            modifier = Modifier
                                .weight(1f)
                                .clickableNoRipple { listener.onAddAmountToSavingsClicked() },
                            isLoading = state.isSummaryLoading,
                            amount = state.summary.unassignedAmount.toInt().toString(),
                            amountColor = Theme.colorScheme.additional.onSuccess,
                            amountTextStyle = Theme.typography.heading.small,
                            title = stringResource(Res.string.unassigned_savings),
                            titleColor = Theme.colorScheme.text.body,
                            titleTextStyle = Theme.typography.label.medium.small,
                            backgroundColor = Theme.colorScheme.brand.secondary.toBrush(),
                            borderColor = Theme.colorScheme.border.primary
                        )
                    }
                }
            }

            if (state.isGoalsLoading) {
                items(5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .shimmerEffect()
                    )
                }
            } else if (state.goals.isNotEmpty()) {
                itemsIndexed(state.goals) { index, goal ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (index == firstCompletedIndex) {
                            Text(
                                text = stringResource(Res.string.history),
                                style = Theme.typography.title.medium,
                                color = Theme.colorScheme.text.titleSmall,
                                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                            )
                        }


                        GoalCard(
                            modifier = Modifier.fillMaxWidth(),
                            icon = goal.icon.toDrawableResource(),
                            title = goal.name,
                            current = goal.currentAmount.toInt(),
                            budgetData = GoalDataUiState(
                                targetDate = goal.deadline.date,
                                priority = goal.priority.toUiState(goal.isCompleted),
                                percentage = goal.savingPercentage,
                                total = goal.targetAmount.toInt()
                            ),
                            onClickMenu = { listener.onGoalClickMenu(goal) }
                        )
                    }
                }
            }

            if (state.isGoalsLoadingMore) {
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

        PaginationTrigger(
            list = state.goals,
            listState = listState,
            remainingItemsToLoadNextPage = 5,
            loadNextItems = listener::onListScrolled
        )

        if (!state.isGoalsLoading && state.goals.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_goals_yet),
                    style = Theme.typography.body.medium,
                    color = Theme.colorScheme.text.body,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    AddEditGoalBottomSheet(
        isVisible = state.isAddGoalBottomSheetVisible,
        onDismiss = listener::onAddGoalBottomSheetDismissed,
        initialAddEditGoalUiState = state.addEditUiState.takeIf { state.goalToEdit != null },
        onAddGoal = listener::onAddEditGoal
    )

    GoalActionsSheet(
        show = state.isGoalActionsSheetVisible,
        onOptionSelected = listener::onGoalActionSelected,
        onDismiss = listener::onGoalActionsSheetDismissed
    )

    AddAmountToGoalBottomSheet(
        isVisible = state.isAddAmountToGoalVisible,
        onDismiss = listener::onDismissAddAmount,
        goalName = state.goalToEdit?.name ?: "",
        initialAmount = null,
        onAddAmount = listener::onAddAmount,
        isLoading = state.addAmountUiState.isLoading,
        errorText = state.addAmountUiState.error?.asString(),
        onAmountChanged = listener::onAddAmountErrorDismissed
    )

    AddAmountToGoalBottomSheet(
        isVisible = state.isAddAmountToSavingsVisible,
        onDismiss = listener::onDismissAddAmountToSavings,
        goalName = stringResource(Res.string.unassigned_savings),
        initialAmount = null,
        onAddAmount = listener::onAddAmountToSavings,
        isLoading = state.addAmountUiState.isLoading,
        errorText = state.addAmountUiState.error?.asString(),
        onAmountChanged = listener::onAddAmountErrorDismissed
    )
}
