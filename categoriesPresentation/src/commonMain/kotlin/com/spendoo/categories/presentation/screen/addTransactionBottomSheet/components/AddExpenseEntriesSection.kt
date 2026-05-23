package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionInteractionListener
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionUiState
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.TransactionType
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.entries
import spendoo.designsystem.generated.resources.ic_plus

@Composable
fun AddExpenseEntriesSection(
    state: AddTransactionUiState,
    interactionListener: AddTransactionInteractionListener
) {
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
                    contentDescription = null
                )
            }
            LazyRow(
                modifier = Modifier.padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                itemsIndexed(state.expenseEntries) { index, entry ->
                    ExpenseEntryItem(
                        index = index,
                        entry = entry,
                        onEntryChanged = interactionListener::onEntryChanged,
                        onCategoryClicked = interactionListener::onCategoryFieldClicked,
                        onRemoveClicked = interactionListener::removeExpenseEntry,
                        showRemoveButton = state.expenseEntries.size > 1
                    )
                }
            }
        }
    }
}