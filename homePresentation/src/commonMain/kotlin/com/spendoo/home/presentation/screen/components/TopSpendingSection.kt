package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.cards.TopSpendingItem
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.home.presentation.screen.SpendingUiState
import com.spendoo.home.presentation.screen.toDrawableResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.no_top_spending_yet
import spendoo.designsystem.generated.resources.top_spending

@Composable
fun TopSpendingSection(
    spending: List<SpendingUiState>,
    isLoading: Boolean,
    onViewAll: () -> Unit,
    onCategoryClicked: (String) -> Unit
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
        } else if (spending.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_top_spending_yet),
                    style = Theme.typography.label.medium.medium,
                    color = Theme.colorScheme.text.titleSmall
                )
            }
        } else {
            spending.forEach { item ->
                TopSpendingItem(
                    icon = item.icon.toDrawableResource(),
                    categoryName = item.categoryName,
                    amount = item.amount.toString(),
                    onClick = { onCategoryClicked(item.id) })
            }
        }
    }
}