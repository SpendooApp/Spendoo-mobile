package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.designsystem.utils.asString
import com.spendoo.designsystem.utils.UiText
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.statistics.presentation.screen.statistics.StatisticsTransactionUiState
import com.spendoo.statistics.presentation.screen.statistics.toDrawableResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.category_label
import spendoo.designsystem.generated.resources.ic_dots
import spendoo.designsystem.generated.resources.ic_money
import com.spendoo.categories.domain.entity.transaction.TransactionType
import com.spendoo.designsystem.components.icon.CategoryIcon as CategoryIconComposable
import spendoo.designsystem.generated.resources.income
import spendoo.designsystem.generated.resources.expenses
import spendoo.designsystem.generated.resources.budget

@Composable
fun TransactionCardItem(
    transaction: StatisticsTransactionUiState,
    onClick: () -> Unit,
    onClickMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconRes = transaction.categoryIcon.toDrawableResource()
    val isExp = transaction.isExpense
    val amountColor =
        if (isExp) Theme.colorScheme.additional.onError else Theme.colorScheme.additional.onSuccess
    val sign = if (isExp) "-" else "+"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.secondary, RoundedCornerShape(24.dp))
            .border(1.dp, Theme.colorScheme.border.primary, RoundedCornerShape(24.dp))
            .clickableNoRipple(onClick = onClick, enabled = transaction.type != TransactionType.BUDGET)
            .padding(horizontal = 17.dp, vertical = 9.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryIconComposable(
                icon = iconRes,
                size = 40.dp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = transaction.title,
                    style = Theme.typography.title.medium,
                    color = Theme.colorScheme.text.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(Theme.colorScheme.background.primary, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.category_label, transaction.categoryName),
                            style = Theme.typography.label.medium.tiny,
                            color = Theme.colorScheme.brand.secondaryVariant
                        )
                    }

                    val typeText = when (transaction.type) {
                        TransactionType.INCOME -> stringResource(Res.string.income)
                        TransactionType.EXPENSE -> stringResource(Res.string.expenses)
                        TransactionType.BUDGET -> stringResource(Res.string.budget)
                    }
                    val typeColor = when (transaction.type) {
                        TransactionType.INCOME -> Theme.colorScheme.additional.onSuccess
                        TransactionType.EXPENSE -> Theme.colorScheme.additional.onError
                        TransactionType.BUDGET -> Theme.colorScheme.additional.purple
                    }

                    Box(
                        modifier = Modifier
                            .background(typeColor.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = typeText,
                            style = Theme.typography.label.medium.tiny,
                            color = typeColor
                        )
                    }
                }
            }

            if (transaction.type != TransactionType.BUDGET) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickableNoRipple(onClick = onClickMenu),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = Res.drawable.ic_dots.painter(),
                        contentDescription = "Menu",
                        tint = Theme.colorScheme.text.body,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(Theme.colorScheme.background.primary, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = transaction.date.asString(),
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.text.body
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .background(amountColor.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = sign,
                    style = Theme.typography.label.medium.small,
                    color = amountColor
                )
                Icon(
                    painter = Res.drawable.ic_money.painter(),
                    contentDescription = null,
                    tint = amountColor,
                    modifier = Modifier.size(11.dp)
                )
                Text(
                    text = transaction.amount,
                    style = Theme.typography.label.medium.small,
                    color = amountColor
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TransactionCardItemPreview() {
    SpendooTheme {
        TransactionCardItem(
            transaction = StatisticsTransactionUiState(
                id = "1",
                title = "Starbucks Coffee",
                amount = "5.50",
                amountColorRed = true,
                date = UiText.DynamicString("2026/06/23  08:30 AM"),
                categoryName = "Food & Drinks",
                categoryIcon = CategoryIcon.COFFEE,
                isExpense = true
            ),
            onClick = {},
            onClickMenu = {}
        )
    }
}
