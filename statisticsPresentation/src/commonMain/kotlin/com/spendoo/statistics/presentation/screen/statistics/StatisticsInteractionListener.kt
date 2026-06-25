package com.spendoo.statistics.presentation.screen.statistics

import com.spendoo.statistics.domain.entity.Granularity

interface StatisticsInteractionListener {
    fun onReload()
    fun onOpenScheduledPayments()
    fun onTabSelected(tab: StatisticsTab)
    fun onGranularitySelected(granularity: Granularity)
    fun onSeeAllTopCategories()
    fun onSeeAllScheduledPayments()
    fun onSearchQueryChanged(query: String)
    fun onTransactionsListScrolled()
    fun onSortClicked()
    fun onSortDismissed()
    fun onSortOptionSelected(option: TransactionSortOption)
    fun onTransactionMenuClicked(transaction: StatisticsTransactionUiState)
    fun onTransactionActionsDismissed()
    fun onEditTransaction(transactionId: String)
    fun onDeleteTransaction(transactionId: String)
    fun onTransactionClicked(transactionId: String)
}
