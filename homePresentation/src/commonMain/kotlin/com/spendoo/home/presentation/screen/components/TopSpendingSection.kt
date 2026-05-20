package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.home.presentation.screen.SpendingUiState
import com.spendoo.home.presentation.screen.toDrawableResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.top_spending

@Composable
fun TopSpendingSection(
    spending: List<SpendingUiState>,
    isLoading: Boolean,
    onViewAll: () -> Unit,
    onSpendingClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionHeader(
            title = stringResource(Res.string.top_spending),
            onViewAll = onViewAll
        )
        if (isLoading) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect()
                )
            }
        } else {
            spending.forEach { item ->
                SpendingItem(spending = item, onClick = { onSpendingClicked(item.id) })
            }
        }
    }
}

@Composable
private fun SpendingItem(spending: SpendingUiState, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Theme.colorScheme.border.primary, RoundedCornerShape(16.dp))
            .background(Theme.colorScheme.background.secondary)
            .clickableNoRipple { onClick() }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryIcon(icon = spending.icon.toDrawableResource())
            Text(
                text = spending.categoryName,
                style = Theme.typography.title.small,
                color = Theme.colorScheme.text.title
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Theme.colorScheme.button.secondary)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = spending.amount.toInt().toString(),
                style = Theme.typography.title.small,
                color = Theme.colorScheme.icon.primary
            )
        }
    }
}
