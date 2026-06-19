package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.badge.TimeLeftBadge
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun CardDetailsHeader(
    title: String,
    icon: DrawableResource,
    timeLeft: String,
    isDueSoon: Boolean,
    iconTint: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CategoryIcon(
            icon = icon,
            iconTint = iconTint,
            size = 48.dp
        )

        Column(
            modifier = Modifier.weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = Theme.typography.title.small,
                color = Theme.colorScheme.text.title,
                maxLines = 1
            )

            TimeLeftBadge(
                timeLeft = timeLeft,
                isDueSoon = isDueSoon
            )
        }
    }
}