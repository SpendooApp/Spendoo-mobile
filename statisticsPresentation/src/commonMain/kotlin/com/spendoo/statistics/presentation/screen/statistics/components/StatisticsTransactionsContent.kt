package com.spendoo.statistics.presentation.screen.statistics.components

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import com.spendoo.statistics.presentation.screen.statistics.StatisticsInteractionListener
import com.spendoo.statistics.presentation.screen.statistics.StatisticsUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_search
import spendoo.designsystem.generated.resources.ic_sort
import spendoo.designsystem.generated.resources.no_transactions_found
import spendoo.designsystem.generated.resources.search_transactions

@Composable
fun StatisticsTransactionsContent(
    state: StatisticsUiState,
    listener: StatisticsInteractionListener,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CustomTextField(
            value = state.searchQuery,
            onValueChange = listener::onSearchQueryChanged,
            hint = stringResource(Res.string.search_transactions),
            leadingIcon = Res.drawable.ic_search.painter(),
            trailingIcon = Res.drawable.ic_sort.painter(),
            onTrailingIconClick = listener::onSortClicked,
            backgroundColor = Theme.colorScheme.background.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        if (state.isTransactionsLoading && state.transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_transactions_found),
                    style = Theme.typography.body.medium,
                    color = Theme.colorScheme.text.body
                )
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.transactions) { transaction ->
                    TransactionCardItem(
                        transaction = transaction,
                        onClick = { listener.onTransactionClicked(transaction.id) },
                        onClickMenu = { listener.onTransactionMenuClicked(transaction) }
                    )
                }

                if (state.isTransactionsLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(140.dp).navigationBarsPadding())
                }
            }

            PaginationTrigger(
                list = state.transactions,
                listState = listState,
                remainingItemsToLoadNextPage = 5,
                loadNextItems = listener::onTransactionsListScrolled
            )
        }
    }
}
