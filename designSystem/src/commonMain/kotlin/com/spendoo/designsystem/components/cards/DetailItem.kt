package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun DetailItem(
    icon: DrawableResource,
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Theme.colorScheme.text.title
) {
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
            Icon(
                painter = icon.painter(),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = Theme.colorScheme.brand.primary
            )
            Text(
                text = value,
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