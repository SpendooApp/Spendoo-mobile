package com.spendoo.statistics.presentation.screen.statistics

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.general.AppSegmentedControl
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.extentions.painter
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
import com.spendoo.statistics.presentation.screen.statistics.components.StatisticsChartsContent
import com.spendoo.statistics.presentation.screen.statistics.components.StatisticsScheduledPaymentUiState
import com.spendoo.statistics.presentation.screen.statistics.components.StatisticsTransactionsContent
import com.spendoo.statistics.presentation.screen.statistics.components.TransactionActionType
import com.spendoo.statistics.presentation.screen.statistics.components.TransactionActionsSheet
import com.spendoo.statistics.presentation.screen.statistics.components.TransactionSortSheet
import kotlinx.datetime.LocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.avatar_me
import spendoo.designsystem.generated.resources.ic_download
import spendoo.designsystem.generated.resources.ic_user_follow

@Composable
fun StatisticsScreen(
    userId: String? = null,
    userName: String? = null,
    userImageUrl: String? = null,
    viewModel: StatisticsViewModel = koinViewModel(parameters = { parametersOf(userId, userName, userImageUrl) })
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.refreshSignal.collect { shouldRefresh ->
            if (shouldRefresh == true) {
                viewModel.onReload()
            }
        }
    }

    PullToRefresh(
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::onReload
    ) {
        StatisticsContent(
            state = state,
            listener = viewModel
        )
    }
}

@Composable
private fun StatisticsContent(
    state: StatisticsUiState,
    listener: StatisticsInteractionListener
) {
    var topBarHeight by remember { mutableStateOf(0) }
    var topBarOffsetHeightPx by remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = topBarOffsetHeightPx + delta
                topBarOffsetHeightPx = newOffset.coerceIn(-topBarHeight.toFloat(), 0f)
                return Offset.Zero
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colorScheme.background.primary)
                .statusBarsPadding()
                .nestedScroll(nestedScrollConnection)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .let { modifier ->
                        if (topBarHeight > 0) {
                            modifier.height(with(density) { (topBarHeight + topBarOffsetHeightPx).coerceAtLeast(0f).toDp() })
                        } else {
                            modifier
                        }
                    }
                    .graphicsLayer {
                        alpha = if (topBarHeight > 0) (1f + topBarOffsetHeightPx / topBarHeight) else 1f
                        translationY = topBarOffsetHeightPx
                    }
            ) {
                TopBar(
                    title = state.userName,
                    onBackClicked = state.targetUserId?.let { listener::onClickBack },
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        if (topBarHeight == 0) {
                            topBarHeight = coordinates.size.height
                        }
                    },
                    leading = {
                        AsyncImage(
                            model = state.userImageUrl,
                            placeholder = Res.drawable.avatar_me.painter(),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(50.dp)
                                .clickableNoRipple(enabled = state.userImageUrl != null) { listener.onClickUserProfile() }
                                .clip(CircleShape)
                                .border(0.5.dp, Theme.colorScheme.button.secondary, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    },
                    actions = listOfNotNull(
                        if (state.targetUserId == null) {
                            {
                                SpendooIconButton(
                                    iconRes = Res.drawable.ic_user_follow,
                                    contentDescription = "Profile",
                                    size = 48.dp,
                                    iconSize = 24.dp,
                                    showBorder = true,
                                    onClick = listener::onFollowUserClicked
                                )
                            }
                        } else null,
                        {
                            SpendooIconButton(
                                iconRes = Res.drawable.ic_download,
                                contentDescription = "Download Report",
                                size = 48.dp,
                                iconSize = 24.dp,
                                showBorder = true,
                                onClick = listener::onDownloadReportClicked
                            )
                        }
                    )
                )
            }

            if (state.targetUserId == null) {
                AppSegmentedControl(
                    options = StatisticsTab.entries,
                    selectedOption = state.selectedTab,
                    onOptionSelected = listener::onTabSelected,
                    getName = { this.toName() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .border(1.dp, Theme.colorScheme.border.secondary, RoundedCornerShape(12.dp))
                )
            }

            AnimatedContent(
                targetState = state.selectedTab,
                modifier = Modifier.fillMaxWidth().weight(1f),
                transitionSpec = {
                    val isReverse = targetState < initialState
                    if (isReverse) {
                        slideInHorizontally { -it } + fadeIn() togetherWith
                                slideOutHorizontally { it } + fadeOut()
                    } else {
                        slideInHorizontally { it } + fadeIn() togetherWith
                                slideOutHorizontally { -it } + fadeOut()
                    }
                }
            ) { tab ->
                when (tab) {
                    StatisticsTab.CHARTS -> {
                        StatisticsChartsContent(
                            state = state,
                            listener = listener
                        )
                    }

                    StatisticsTab.TRANSACTIONS -> {
                        StatisticsTransactionsContent(
                            state = state,
                            listener = listener
                        )
                    }
                }
            }
        }

        TransactionSortSheet(
            show = state.isSortSheetVisible,
            selectedOption = state.selectedSortOption,
            onOptionSelected = listener::onSortOptionSelected,
            onDismiss = listener::onSortDismissed
        )

        state.selectedTransaction?.let { transaction ->
            TransactionActionsSheet(
                show = state.isActionsSheetVisible,
                isDeletable = transaction.isDeletable,
                onOptionSelected = { action ->
                    when (action) {
                        TransactionActionType.Edit -> listener.onEditTransaction(transaction.id)
                        TransactionActionType.Delete -> listener.onDeleteTransaction(transaction.id)
                    }
                },
                onDismiss = listener::onTransactionActionsDismissed
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun StatisticsContentTransactionsPreview() {
    SpendooTheme {
        StatisticsContent(
            state = StatisticsUiState(
                selectedTab = StatisticsTab.TRANSACTIONS,
                transactions = listOf(
                    StatisticsTransactionUiState(
                        id = "1",
                        title = "Starbucks Coffee",
                        amount = "5.50",
                        amountColorRed = true,
                        date = UiText.DynamicString("2026/06/23  08:30 AM"),
                        categoryName = "Food & Drinks",
                        categoryIcon = CategoryIcon.COFFEE,
                        isExpense = true
                    ),
                    StatisticsTransactionUiState(
                        id = "2",
                        title = "Salary Deposit",
                        amount = "3000.00",
                        amountColorRed = false,
                        date = UiText.DynamicString("2026/06/23  09:00 AM"),
                        categoryName = "Salary",
                        categoryIcon = CategoryIcon.DEFAULT,
                        isExpense = false
                    )
                )
            ),
            listener = object : StatisticsInteractionListener {
                override fun onReload() {}
                override fun onClickBack() {}
                override fun onDownloadReportClicked() {}
                override fun onFollowUserClicked() {}
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
                override fun onClickUserProfile() {}
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun StatisticsContentChartsPreview() {
    val mockLocalDateTime = LocalDateTime(2026, 6, 23, 12, 0)
    SpendooTheme {
        StatisticsContent(
            state = StatisticsUiState(
                selectedTab = StatisticsTab.CHARTS,
                selectedGranularity = Granularity.MONTH,
                combinedStats = CombinedStats(
                    financialStats = FinancialStats(
                        buckets = listOf(
                            StatsBucket(
                                spending = 150.0,
                                income = 200.0,
                                budget = 250.0,
                                startDate = mockLocalDateTime,
                                isPredicted = false
                            ),
                            StatsBucket(
                                spending = 220.0,
                                income = 300.0,
                                budget = 250.0,
                                startDate = mockLocalDateTime,
                                isPredicted = false
                            ),
                            StatsBucket(
                                spending = 180.0,
                                income = 250.0,
                                budget = 250.0,
                                startDate = mockLocalDateTime,
                                isPredicted = false
                            ),
                            StatsBucket(
                                spending = 280.0,
                                income = 400.0,
                                budget = 250.0,
                                startDate = mockLocalDateTime,
                                isPredicted = true
                            )
                        ),
                        highestSpendingBucketIndex = 3,
                        highestValue = 400.0
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
                override fun onClickBack() {}
                override fun onDownloadReportClicked() {}
                override fun onFollowUserClicked() {}
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
                override fun onClickUserProfile() {}
            }
        )
    }
}
