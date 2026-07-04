package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionInteractionListener
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionUiState
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.TransactionType
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.entries
import spendoo.designsystem.generated.resources.ic_plus

@Composable
fun AddExpenseEntriesSection(
    state: AddTransactionUiState,
    interactionListener: AddTransactionInteractionListener
) {
    val listState = rememberLazyListState()

    // Uniform height tracking
    val itemHeights = remember { mutableStateMapOf<String, Dp>() }
    val maxHeight by remember {
        derivedStateOf { itemHeights.values.maxOrNull() ?: 0.dp }
    }

    // Clean-up heights when items are removed
    LaunchedEffect(state.expenseEntries, state.isProcessingMedia) {
        val currentIds = state.expenseEntries.map { it.id }.toSet()
        val keysToRemove = itemHeights.keys.filter { it !in currentIds }
        keysToRemove.forEach { itemHeights.remove(it) }
    }

    LaunchedEffect(state.isProcessingMedia) {
        // Only auto-scroll when processing starts
        if (state.isProcessingMedia) {
            // Wait a tiny frame for the LazyColumn to register the new shimmer item
            snapshotFlow { listState.layoutInfo.totalItemsCount }
                .first { it > state.expenseEntries.size }

            // Safely scroll to the very last item structural index (the shimmer)
            val totalItems = listState.layoutInfo.totalItemsCount
            if (totalItems > 0) {
                listState.animateScrollToItem(totalItems - 1)
            }
        }
    }

    AnimatedVisibility(
        visible = state.type == TransactionType.Expense,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.entries),
                    style = Theme.typography.label.medium.medium,
                    color = Theme.colorScheme.text.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                SpendooIconButton(
                    onClick = interactionListener::addExpenseEntry,
                    iconRes = Res.drawable.ic_plus,
                    tint = Theme.colorScheme.button.primary,
                    iconSize = 24.dp,
                    size = 48.dp,
                    showBorder = false,
                    backgroundColor = Theme.colorScheme.button.secondary,
                    contentDescription = null,
                    enabled = !state.isProcessingMedia
                )
            }
            LazyRow(
                modifier = Modifier.padding(bottom = 12.dp).animateContentSize(),
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                itemsIndexed(state.expenseEntries, key = { _, entry -> entry.id }) { index, entry ->
                    ExpenseEntryItem(
                        modifier = Modifier
                            .animateItem()
                            .heightIn(min = maxHeight),
                        index = index,
                        entry = entry,
                        savedTitles = state.savedExpenseTitles,
                        onEntryChanged = interactionListener::onEntryChanged,
                        onCategoryClicked = interactionListener::onCategoryFieldClicked,
                        onRemoveClicked = interactionListener::removeExpenseEntry,
                        showRemoveButton = state.expenseEntries.size > 1 && !state.isProcessingMedia,
                        onHeightChanged = { height ->
                            if (itemHeights[entry.id] != height) {
                                itemHeights[entry.id] = height
                            }
                        }
                    )
                }

                if (state.isProcessingMedia) {
                    item(key = "shimmer") {
                        ExpenseEntryItemShimmer(
                            modifier = Modifier
                                .animateItem()
                                .heightIn(min = maxHeight)
                        )
                    }
                }
            }
        }
    }
}
