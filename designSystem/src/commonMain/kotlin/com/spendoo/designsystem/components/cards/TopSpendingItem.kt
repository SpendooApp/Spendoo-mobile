package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun TopSpendingItem(
    icon: DrawableResource,
    categoryName: String,
    amount: String,
    onClick: () -> Unit
) {
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
            CategoryIcon(icon = icon)
            Text(
                text = categoryName,
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
                text = amount,
                style = Theme.typography.title.small,
                color = Theme.colorScheme.icon.primary
            )
        }
    }
}
