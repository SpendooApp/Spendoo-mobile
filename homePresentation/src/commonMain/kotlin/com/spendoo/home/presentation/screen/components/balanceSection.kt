package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.cards.MoneyCard
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.theme.color.scheme.toBrush
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.home.presentation.screen.BalanceSummaryUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.expenses
import spendoo.designsystem.generated.resources.ic_arrow_diagonal_up
import spendoo.designsystem.generated.resources.income
import spendoo.designsystem.generated.resources.total_balance

fun LazyListScope.balanceSection(
    balanceSummary: BalanceSummaryUiState,
    isLoading: Boolean
) {
    item {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MoneyCard(
                modifier = Modifier.fillMaxWidth(),
                isLoading = isLoading,
                amount = balanceSummary.totalBalance.toInt().toString(),
                amountColor = Theme.colorScheme.brand.onPrimary,
                amountTextStyle = Theme.typography.heading.large,
                title = stringResource(Res.string.total_balance),
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
                    isLoading = isLoading,
                    amount = balanceSummary.expenses.toInt().toString(),
                    amountColor = Theme.colorScheme.brand.onSecondary,
                    amountTextStyle = Theme.typography.heading.medium,
                    title = stringResource(Res.string.expenses),
                    titleColor = Theme.colorScheme.brand.secondaryVariant,
                    titleTextStyle = Theme.typography.body.small,
                    titleIcon = {
                        Icon(
                            modifier = Modifier.size(16.dp, 22.dp).rotate(180f),
                            painter = Res.drawable.ic_arrow_diagonal_up.painter(),
                            contentDescription = null,
                            tint = Theme.colorScheme.additional.onError,
                        )
                    },
                    backgroundColor = Theme.colorScheme.brand.secondary.toBrush(),
                    borderColor = Theme.colorScheme.border.primary
                )
                MoneyCard(
                    modifier = Modifier.weight(1f),
                    isLoading = isLoading,
                    amount = balanceSummary.income.toInt().toString(),
                    amountColor = Theme.colorScheme.brand.onSecondary,
                    amountTextStyle = Theme.typography.heading.medium,
                    title = stringResource(Res.string.income),
                    titleColor = Theme.colorScheme.brand.secondaryVariant,
                    titleTextStyle = Theme.typography.body.small,
                    titleIcon = {
                        Icon(
                            modifier = Modifier.size(16.dp, 22.dp),
                            painter = Res.drawable.ic_arrow_diagonal_up.painter(),
                            contentDescription = null,
                            tint = Theme.colorScheme.additional.onSuccess,
                        )
                    },
                    backgroundColor = Theme.colorScheme.brand.secondary.toBrush(),
                    borderColor = Theme.colorScheme.border.primary
                )
            }
        }
    }
}
