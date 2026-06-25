package com.spendoo.statistics.presentation.screen.statistics

import com.spendoo.categories.api.ScheduledPaymentsRoute
import com.spendoo.categories.api.TransactionDetailsRoute
import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.categories.domain.entity.transaction.TransactionType
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.toUiText
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.getNow
import com.spendoo.shared.domain.utils.getToday
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.repository.StatisticsRepository
import com.spendoo.identity.domain.repository.ProfileRepository
import kotlinx.datetime.daysUntil
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlinx.datetime.minus
import kotlinx.datetime.atTime
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.due_in_days
import spendoo.designsystem.generated.resources.error_loading_statistics
import spendoo.designsystem.generated.resources.overdue
import spendoo.designsystem.generated.resources.today
import spendoo.designsystem.generated.resources.tomorrow
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.done
import androidx.lifecycle.viewModelScope
import com.spendoo.categories.api.CategoriesRoute
import com.spendoo.statistics.presentation.screen.statistics.components.StatisticsScheduledPaymentUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class StatisticsViewModel(
    private val statisticsRepository: StatisticsRepository,
    private val scheduledPaymentsRepository: ScheduledPaymentsRepository,
    private val transactionsRepository: TransactionsRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel<StatisticsUiState>(StatisticsUiState()), StatisticsInteractionListener {

    private val searchQueryFlow = MutableStateFlow("")

    private val transactionsPaginator = createPaginator(
        initialKey = INITIAL_PAGE,
        pageSize = PAGE_SIZE,
        loadPage = { page ->
            transactionsRepository.getTransactions(
                search = searchQueryFlow.value.takeIf { it.isNotBlank() },
                pageQuery = PageQuery(
                    page = page,
                    size = PAGE_SIZE,
                    sort = state.value.selectedSortOption.sortParams
                )
            ).data
        },
        onSuccess = { items ->
            val mapped = items.map { it.toUiState() }
            updateState {
                copy(
                    transactions = transactions + mapped,
                    isTransactionsLoading = false,
                    isTransactionsLoadingMore = false
                )
            }
        },
        onLoadUpdated = { isLoading ->
            updateState {
                if (transactions.isEmpty()) {
                    copy(isTransactionsLoading = isLoading)
                } else {
                    copy(isTransactionsLoadingMore = isLoading)
                }
            }
        },
        onError = {
            updateState {
                copy(
                    isTransactionsLoading = false,
                    isTransactionsLoadingMore = false
                )
            }
            showSnackBar(
                title = UiText.StringRes(Res.string.an_error_occurred),
                isSuccess = false
            )
        }
    )

    init {
        listenToResetSignal()
        onReload()
        setupSearchDebounce()
    }

    private fun listenToResetSignal() {
        tryToCollect(
            block = {
                getResult<Boolean?>("reset", consume = true)
            },
            onEach = { shouldReset ->
                if (shouldReset == true) {
                    onReload()
                }
            },
            onError = {}
        )
        tryToCollect(
            block = {
                getResult<Boolean?>("resetScheduledPayments", consume = true)
            },
            onEach = { shouldReset ->
                if (shouldReset == true) {
                    loadScheduledPayments()
                }
            },
            onError = {}
        )
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchDebounce() {
        tryToCollect(
            block = {
                searchQueryFlow
                    .debounce(SEARCH_DEBOUNCE_DELAY_MS.milliseconds)
                    .distinctUntilChanged()
            },
            onEach = {
                resetAndLoadTransactions()
            },
            onError = {}
        )
    }

    override fun onReload() {
        loadUserProfile()
        loadStatistics(state.value.selectedGranularity)
        loadScheduledPayments()
        resetAndLoadTransactions()
    }

    private fun resetAndLoadTransactions() {
        transactionsPaginator.reset()
        updateState { copy(transactions = emptyList()) }
        viewModelScope.launch {
            transactionsPaginator.loadNextItems()
        }
    }

    private fun loadUserProfile() {
        tryToCall(
            block = { profileRepository.getProfile() },
            onSuccess = { profile ->
                updateState {
                    copy(
                        userName = profile.fullName,
                        userImageUrl = profile.imageUrl
                    )
                }
            },
            onError = {}
        )
    }

    private fun loadStatistics(granularity: Granularity) {
        val today = getToday()
        val start = when (granularity) {
            Granularity.DAY -> today.minus(9, DateTimeUnit.DAY)
            Granularity.WEEK -> today.minus(9, DateTimeUnit.WEEK)
            Granularity.MONTH -> today.minus(9, DateTimeUnit.MONTH)
            Granularity.YEAR -> today.minus(9, DateTimeUnit.YEAR)
        }
        val end = when (granularity) {
            Granularity.DAY -> today.plus(2, DateTimeUnit.DAY)
            Granularity.WEEK -> today.plus(2, DateTimeUnit.WEEK)
            Granularity.MONTH -> today.plus(2, DateTimeUnit.MONTH)
            Granularity.YEAR -> today.plus(2, DateTimeUnit.YEAR)
        }
        val startDateTime = start.atTime(0, 0, 0)
        val endDateTime = end.atTime(23, 59, 59)

        updateState { copy(isLoading = true) }
        tryToCall(
            block = { statisticsRepository.getStatistics(granularity, startDateTime, endDateTime) },
            onSuccess = { stats ->
                val lineChartUiState = LineChartUiState(
                    budgetData = stats.financialStats.buckets.map { it.budget },
                    spentData = stats.financialStats.buckets.map { it.spending },
                    incomeData = stats.financialStats.buckets.map { it.income },
                    xAxisLabels = getXAxisLabels(stats.financialStats.buckets, granularity),
                    highestSpendingBucketIndex = stats.financialStats.highestSpendingBucketIndex,
                    dashedRanges = getDashedRanges(stats.financialStats.buckets)
                )

                val barChartUiState = BarChartUiState(
                    buckets = stats.budgetStatus.buckets.map { bucket ->
                        BarChartBucketUiState(
                            spending = bucket.spending,
                            status = bucket.status
                        )
                    },
                    xAxisLabels = getBarXLabels(stats.budgetStatus.buckets, granularity)
                )

                val pieChartUiState = stats.topCategories.topCategories.map { category ->
                    PieChartUiState(
                        spending = category.spending,
                        categoryName = category.categoryName
                    )
                }

                updateState {
                    copy(
                        combinedStats = stats,
                        lineChartUiState = lineChartUiState,
                        barChartUiState = barChartUiState,
                        pieChartUiState = pieChartUiState,
                        isLoading = false
                    )
                }
            },
            onError = { throwable ->
                updateState { copy(isLoading = false) }
                showSnackBar(
                    title = Res.string.error_loading_statistics.toUiText(),
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    private fun loadScheduledPayments() {
        updateState { copy(isScheduledPaymentsError = false) }
        tryToCall(
            block = { scheduledPaymentsRepository.getScheduledPayments(PageQuery(page = 0, size = 10)) },
            onSuccess = { pagedPayments ->
                val now = getNow()
                val mapped = pagedPayments.data.map { it.toUiState(now) }
                updateState { copy(scheduledPayments = mapped, isScheduledPaymentsError = false) }
            },
            onError = {
                updateState { copy(isScheduledPaymentsError = true) }
            }
        )
    }

    override fun onTabSelected(tab: StatisticsTab) {
        updateState { copy(selectedTab = tab) }
    }

    override fun onGranularitySelected(granularity: Granularity) {
        if (state.value.selectedGranularity != granularity) {
            updateState { copy(selectedGranularity = granularity) }
            loadStatistics(granularity)
        }
    }

    override fun onSeeAllTopCategories() {
        navigate(CategoriesRoute)
    }

    override fun onSeeAllScheduledPayments() {
        navigate(ScheduledPaymentsRoute)
    }

    override fun onOpenScheduledPayments() {
        navigate(ScheduledPaymentsRoute)
    }

    override fun onSearchQueryChanged(query: String) {
        updateState { copy(searchQuery = query) }
        searchQueryFlow.value = query
    }

    override fun onTransactionsListScrolled() {
        viewModelScope.launch {
            transactionsPaginator.loadNextItems()
        }
    }

    override fun onSortClicked() {
        updateState { copy(isSortSheetVisible = true) }
    }

    override fun onSortDismissed() {
        updateState { copy(isSortSheetVisible = false) }
    }

    override fun onSortOptionSelected(option: TransactionSortOption) {
        updateState { copy(selectedSortOption = option, isSortSheetVisible = false) }
        resetAndLoadTransactions()
    }

    override fun onTransactionMenuClicked(transaction: StatisticsTransactionUiState) {
        updateState { copy(selectedTransaction = transaction, isActionsSheetVisible = true) }
    }

    override fun onTransactionActionsDismissed() {
        updateState { copy(isActionsSheetVisible = false) }
    }

    override fun onEditTransaction(transactionId: String) {
        updateState { copy(isActionsSheetVisible = false) }
        // TODO: Navigate to Edit Transaction screen when route is added
    }

    override fun onDeleteTransaction(transactionId: String) {
        updateState { copy(isActionsSheetVisible = false) }
        tryToCall(
            block = { transactionsRepository.deleteTransaction(transactionId) },
            onSuccess = {
                resetAndLoadTransactions()
                showSnackBar(
                    title = UiText.StringRes(Res.string.done),
                    isSuccess = true
                )
            },
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    isSuccess = false
                )
            }
        )
    }

    override fun onTransactionClicked(transactionId: String) {
        navigate(TransactionDetailsRoute(transactionId))
    }

    companion object {
        const val INITIAL_PAGE = 0
        const val PAGE_SIZE = 20
        const val SEARCH_DEBOUNCE_DELAY_MS = 300L
    }
}
