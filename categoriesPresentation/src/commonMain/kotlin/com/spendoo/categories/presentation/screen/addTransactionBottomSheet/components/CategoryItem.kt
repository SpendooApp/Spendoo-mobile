package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun CategoryItem(
    category: CategoryItemUiState,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Theme.colorScheme.button.primary.copy(alpha = 0.1f) else Theme.colorScheme.background.tertiary)
            .border(
                width = 1.dp,
                color = if (isSelected) Theme.colorScheme.button.primary else Theme.colorScheme.border.secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickableNoRipple(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CategoryIcon(
            icon = category.icon.toDrawableResource(),
            size = 40.dp
        )
        Text(
            text = category.name,
            style = Theme.typography.label.medium.medium,
            color = Theme.colorScheme.text.titleSmall
        )
    }
}

@Composable
fun CategoryItemShimmer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Theme.colorScheme.background.tertiary)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
        )
        Box(
            modifier = Modifier.height(20.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
        )
    }
}