package com.spendoo.categories.presentation.screen.transactionDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.domain.entity.transaction.TransactionType
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.cards.DetailItem
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.shared.domain.entity.CategoryIcon as DomainCategoryIcon
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.category_label_plain
import spendoo.designsystem.generated.resources.date_label
import spendoo.designsystem.generated.resources.delete
import spendoo.designsystem.generated.resources.edit
import spendoo.designsystem.generated.resources.ic_clock_red
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.ic_delete
import spendoo.designsystem.generated.resources.ic_edit
import spendoo.designsystem.generated.resources.ic_money
import spendoo.designsystem.generated.resources.money_amount
import spendoo.designsystem.generated.resources.note_label
import spendoo.designsystem.generated.resources.time_label

@Composable
fun TransactionDetailsScreen(
    transactionId: String,
    viewModel: TransactionDetailsViewModel = koinViewModel(parameters = { parametersOf(transactionId) })
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TransactionDetailsContent(
        state = state,
        interactionListener = viewModel
    )
}

@Composable
private fun TransactionDetailsContent(
    state: TransactionDetailsUiState,
    interactionListener: TransactionDetailsInteractionListener
) {
    val actions: List<@Composable RowScope.() -> Unit> = if (state.isDeletable) {
        listOf(
            {
                SpendooIconButton(
                    iconRes = Res.drawable.ic_edit,
                    contentDescription = stringResource(Res.string.edit),
                    backgroundColor = Theme.colorScheme.brand.secondary,
                    iconSize = 20.dp,
                    tint = Theme.colorScheme.icon.primary,
                    onClick = interactionListener::onEditClicked
                )
            },
            {
                SpendooIconButton(
                    iconRes = Res.drawable.ic_delete,
                    contentDescription = stringResource(Res.string.delete),
                    backgroundColor = Theme.colorScheme.brand.secondary,
                    iconSize = 20.dp,
                    tint = Theme.colorScheme.icon.primary,
                    onClick = interactionListener::onDeleteClicked
                )
            }
        )
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "",
            onBackClicked = interactionListener::onBackClicked,
            actions = actions
        )

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Theme.colorScheme.background.secondary, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CategoryIcon(
                            icon = state.categoryIcon.toDrawableResource(),
                            size = 48.dp
                        )
                        Text(
                            text = state.title,
                            style = Theme.typography.title.small,
                            color = Theme.colorScheme.text.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TransactionDetailGrid(
                        amount = state.amount,
                        isExpense = state.type == TransactionType.EXPENSE,
                        categoryName = state.categoryName,
                        categoryIcon = state.categoryIcon,
                        dateText = state.dateText,
                        timeText = state.timeText,
                        noteText = state.note,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailItemWithAmount(
    amount: String,
    isExpense: Boolean,
    label: String,
    modifier: Modifier = Modifier
) {
    val valueColor = Theme.colorScheme.text.title
    val sign = if (isExpense) "-" else "+"
    Column(
        modifier = modifier
            .background(Theme.colorScheme.button.secondary, RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = sign,
                style = Theme.typography.label.medium.medium,
                color = valueColor
            )
            Icon(
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = Theme.colorScheme.icon.primary
            )
            Text(
                text = amount,
                style = Theme.typography.label.medium.medium,
                color = valueColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = label,
            style = Theme.typography.label.medium.extraSmall,
            color = Theme.colorScheme.text.body,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TransactionDetailGrid(
    amount: String,
    isExpense: Boolean,
    categoryName: String,
    categoryIcon: DomainCategoryIcon,
    dateText: String,
    timeText: String,
    noteText: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailItemWithAmount(
                amount = amount,
                isExpense = isExpense,
                label = stringResource(Res.string.money_amount),
                modifier = Modifier.weight(1f)
            )
            DetailItem(
                icon = categoryIcon.toDrawableResource(),
                value = categoryName,
                label = stringResource(Res.string.category_label_plain),
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailItem(
                icon = Res.drawable.ic_date,
                value = dateText,
                label = stringResource(Res.string.date_label),
                modifier = Modifier.weight(1f)
            )
            DetailItem(
                icon = Res.drawable.ic_clock_red,
                value = timeText,
                label = stringResource(Res.string.time_label),
                modifier = Modifier.weight(1f)
            )
        }
        if (!noteText.isNullOrBlank()) {
            DetailItem(
                icon = Res.drawable.ic_edit,
                value = noteText,
                label = stringResource(Res.string.note_label),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun TransactionDetailsScreenPreview() = SpendooTheme {
    TransactionDetailsContent(
        state = TransactionDetailsUiState(
            isLoading = false,
            title = "Morning Cafe",
            amount = "200",
            categoryName = "Drinks",
            categoryIcon = DomainCategoryIcon.COFFEE,
            dateText = "Jul 23, 2025",
            timeText = "5:30 PM",
            note = "with Joseph before work",
            type = TransactionType.EXPENSE,
            isDeletable = true
        ),
        interactionListener = object : TransactionDetailsInteractionListener {
            override fun onBackClicked() {}
            override fun onEditClicked() {}
            override fun onDeleteClicked() {}
        }
    )
}
