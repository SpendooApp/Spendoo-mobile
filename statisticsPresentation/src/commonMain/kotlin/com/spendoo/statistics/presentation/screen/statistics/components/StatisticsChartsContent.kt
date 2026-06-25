package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.row.TabsRow
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonSize
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.divider.HorizontalDivider
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.placeholder.EmptyState
import com.spendoo.designsystem.components.placeholder.ErrorState
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.UiText
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.statistics.domain.entity.BudgetStatus
import com.spendoo.statistics.domain.entity.BudgetStatusBucket
import com.spendoo.statistics.domain.entity.BudgetStatusInfo
import com.spendoo.statistics.domain.entity.CategorySpending
import com.spendoo.statistics.domain.entity.CombinedStats
import com.spendoo.statistics.domain.entity.FinancialStats
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.entity.StatsBucket
import com.spendoo.statistics.domain.entity.TopCategories
import com.spendoo.statistics.presentation.screen.statistics.StatisticsInteractionListener
import com.spendoo.statistics.presentation.screen.statistics.StatisticsTab
import com.spendoo.statistics.presentation.screen.statistics.StatisticsTransactionUiState
import com.spendoo.statistics.presentation.screen.statistics.StatisticsUiState
import com.spendoo.statistics.presentation.screen.statistics.TransactionSortOption
import com.spendoo.statistics.presentation.screen.statistics.LineChartUiState
import com.spendoo.statistics.presentation.screen.statistics.BarChartUiState
import com.spendoo.statistics.presentation.screen.statistics.BarChartBucketUiState
import com.spendoo.statistics.presentation.screen.statistics.PieChartUiState
import com.spendoo.statistics.presentation.screen.statistics.toName
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.scheduled_payments
import spendoo.designsystem.generated.resources.see_all
import spendoo.designsystem.generated.resources.top_categories
import spendoo.designsystem.generated.resources.no_scheduled_payments_yet
import spendoo.designsystem.generated.resources.error_loading_scheduled_payments
import spendoo.designsystem.generated.resources.add_scheduled_payment
import spendoo.designsystem.generated.resources.retry

@Composable
fun StatisticsChartsContent(
    state: StatisticsUiState,
    listener: StatisticsInteractionListener,
    animateChart: Boolean = true,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            TabsRow(
                entries = Granularity.entries,
                toName = { this.toName() },
                selectedGranularity = state.selectedGranularity,
                onGranularitySelected = listener::onGranularitySelected
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        if (state.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            state.combinedStats?.let { stats ->
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    state.lineChartUiState?.let { lineState ->
                        LineChartSection(
                            lineChartUiState = lineState,
                            animateChart = animateChart,
                            pointsGap = 30.dp
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }

                item {
                    state.barChartUiState?.let { barState ->
                        BarChartSection(
                            barChartUiState = barState,
                            animateChart = animateChart
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }
                item {
                    DonutChartSection(
                        pieChartUiStates = state.pieChartUiState,
                        animateChart = animateChart,
                        topCategories = stats.topCategories,
                        selectedGranularity = state.selectedGranularity
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    SeeAllHeader(
                        title = stringResource(Res.string.top_categories),
                        onSeeAll = listener::onSeeAllTopCategories,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        stats.topCategories.topCategories.forEach { category ->
                            TopCategoryCardItem(
                                category = category,
                            )
                        }
                    }
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    )

                    SeeAllHeader(
                        title = stringResource(Res.string.scheduled_payments),
                        onSeeAll = listener::onSeeAllScheduledPayments,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                when {
                    state.isScheduledPaymentsError -> {
                        item {
                            ErrorState(
                                text = Res.string.error_loading_scheduled_payments,
                                onActionText = Res.string.retry,
                                onRetry = listener::onReload,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                    state.scheduledPayments.isEmpty() -> {
                        item {
                            EmptyState(
                                text = Res.string.no_scheduled_payments_yet,
                                onActionText = Res.string.add_scheduled_payment,
                                onAddClick = listener::onSeeAllScheduledPayments,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                    else -> {
                        items(state.scheduledPayments) { payment ->
                            ScheduledPaymentRowItem(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                                payment = payment,
                                onClick = listener::onOpenScheduledPayments
                            )
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(140.dp).navigationBarsPadding())
        }
    }
}

@Composable
private fun SeeAllHeader(
    title: String,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Theme.typography.heading.tiny,
            color = Theme.colorScheme.text.titleSmall
        )
        Text(
            text = stringResource(Res.string.see_all),
            style = Theme.typography.label.medium.small,
            color = Theme.colorScheme.text.titleSmall,
            modifier = Modifier.clickableNoRipple { onSeeAll() }
        )
    }
}





@Preview(name = "Light", heightDp = 2000, widthDp = 360)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL, heightDp = 2000, widthDp = 360)
@Composable
private fun StatisticsChartsContentPreview() {
    val mockLocalDateTime = LocalDateTime(2026, 6, 23, 12, 0)
    SpendooTheme {
        StatisticsChartsContent(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colorScheme.background.primary),
            animateChart = false,
            state = StatisticsUiState(
                selectedTab = StatisticsTab.CHARTS,
                selectedGranularity = Granularity.MONTH,
                combinedStats = CombinedStats(
                    financialStats = FinancialStats(
                        buckets = listOf(
                            StatsBucket(
                                9000.0,
                                5000.0,
                                9000.0,
                                LocalDateTime(2026, 1, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                19000.0,
                                0.0,
                                17000.0,
                                LocalDateTime(2026, 2, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                17000.0,
                                12000.0,
                                15000.0,
                                LocalDateTime(2026, 3, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                16000.0,
                                0.0,
                                16000.0,
                                LocalDateTime(2026, 4, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                18000.0,
                                8000.0,
                                24000.0,
                                LocalDateTime(2026, 5, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                25000.0,
                                0.0,
                                26000.0,
                                LocalDateTime(2026, 6, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                30000.0,
                                15000.0,
                                22000.0,
                                LocalDateTime(2026, 7, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                25000.0,
                                0.0,
                                21000.0,
                                LocalDateTime(2026, 8, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                24000.0,
                                10000.0,
                                28000.0,
                                LocalDateTime(2026, 9, 1, 0, 0),
                                false
                            ),
                            StatsBucket(
                                25000.0,
                                0.0,
                                33000.0,
                                LocalDateTime(2026, 10, 1, 0, 0),
                                true
                            ),
                            StatsBucket(
                                32000.0,
                                0.0,
                                33000.0,
                                LocalDateTime(2026, 11, 1, 0, 0),
                                true
                            ),
                            StatsBucket(
                                39000.0,
                                0.0,
                                33000.0,
                                LocalDateTime(2026, 12, 1, 0, 0),
                                true
                            )
                        ),
                        highestSpendingBucketIndex = 6,
                        highestValue = 40000.0
                    ),
                    budgetStatus = BudgetStatusInfo(
                        buckets = listOf(
                            BudgetStatusBucket(
                                spending = 150.0,
                                status = BudgetStatus.WITHIN,
                                percentage = 60.0,
                                startDate = mockLocalDateTime
                            ),
                            BudgetStatusBucket(
                                spending = 220.0,
                                status = BudgetStatus.RISK,
                                percentage = 88.0,
                                startDate = mockLocalDateTime
                            ),
                            BudgetStatusBucket(
                                spending = 280.0,
                                status = BudgetStatus.OVERSPEND,
                                percentage = 112.0,
                                startDate = mockLocalDateTime
                            )
                        ),
                        highestSpending = 280.0
                    ),
                    topCategories = TopCategories(
                        totalSpending = 550.0,
                        topCategories = listOf(
                            CategorySpending(
                                categoryId = "1",
                                categoryName = "Food & Dining",
                                categoryIcon = CategoryIcon.FOOD,
                                spending = 250.0,
                                percentageChange = 12.0,
                                contributionPercentage = 45.0
                            ),
                            CategorySpending(
                                categoryId = "2",
                                categoryName = "Transport",
                                categoryIcon = CategoryIcon.TRAVEL,
                                spending = 150.0,
                                percentageChange = -5.0,
                                contributionPercentage = 27.0
                            ),
                            CategorySpending(
                                categoryId = "3",
                                categoryName = "Entertainment",
                                categoryIcon = CategoryIcon.ENTERTAINMENT,
                                spending = 100.0,
                                percentageChange = 20.0,
                                contributionPercentage = 18.0
                            )
                        )
                    )
                ),
                lineChartUiState = LineChartUiState(
                    budgetData = listOf(9000.0, 19000.0, 17000.0),
                    spentData = listOf(5000.0, 0.0, 12000.0),
                    incomeData = listOf(9000.0, 17000.0, 15000.0),
                    xAxisLabels = listOf(UiText.DynamicString("Jan"), UiText.DynamicString("Feb"), UiText.DynamicString("Mar")),
                    highestSpendingBucketIndex = 2,
                    dashedRanges = emptyList()
                ),
                barChartUiState = BarChartUiState(
                    buckets = listOf(
                        BarChartBucketUiState(1000.0, BudgetStatus.WITHIN),
                        BarChartBucketUiState(1500.0, BudgetStatus.RISK),
                        BarChartBucketUiState(2000.0, BudgetStatus.OVERSPEND)
                    ),
                    xAxisLabels = listOf(UiText.DynamicString("Jan"), UiText.DynamicString("Feb"), UiText.DynamicString("Mar"))
                ),
                pieChartUiState = listOf(
                    PieChartUiState(200.0, "Food"),
                    PieChartUiState(150.0, "Transport"),
                    PieChartUiState(100.0, "Entertainment")
                ),
                scheduledPayments = listOf(
                    StatisticsScheduledPaymentUiState(
                        id = "1",
                        name = "Netflix Subscription",
                        categoryIcon = CategoryIcon.ENTERTAINMENT,
                        amount = "15.99",
                        date = UiText.DynamicString("Monthly"),
                        dueDateText = UiText.DynamicString("Due in 3 days"),
                        isDueSoon = false
                    ),
                    StatisticsScheduledPaymentUiState(
                        id = "2",
                        name = "Spotify Family",
                        categoryIcon = CategoryIcon.DEFAULT,
                        amount = "14.99",
                        date = UiText.DynamicString("Monthly"),
                        dueDateText = UiText.DynamicString("Overdue"),
                        isDueSoon = true
                    )
                )
            ),
            listener = object : StatisticsInteractionListener {
                override fun onReload() {}
                override fun onOpenScheduledPayments() {}
                override fun onTabSelected(tab: StatisticsTab) {}
                override fun onGranularitySelected(granularity: Granularity) {}
                override fun onSeeAllTopCategories() {}
                override fun onSeeAllScheduledPayments() {}
                override fun onSearchQueryChanged(query: String) {}
                override fun onTransactionsListScrolled() {}
                override fun onSortClicked() {}
                override fun onSortDismissed() {}
                override fun onSortOptionSelected(option: TransactionSortOption) {}
                override fun onTransactionMenuClicked(transaction: StatisticsTransactionUiState) {}
                override fun onTransactionActionsDismissed() {}
                override fun onEditTransaction(transactionId: String) {}
                override fun onDeleteTransaction(transactionId: String) {}
                override fun onTransactionClicked(transactionId: String) {}
            }
        )
    }
}
