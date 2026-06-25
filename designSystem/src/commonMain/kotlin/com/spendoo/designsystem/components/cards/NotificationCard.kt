package com.spendoo.designsystem.components.cards


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_warning



@Composable
fun NotificationCard(
    icon: DrawableResource,
    title: String,
    description: String,
    current: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(24.dp)
) {
    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.dp, Theme.colorScheme.border.primary, shape)
            .padding( 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = icon.painter(),
                contentDescription = null,
                tint = Theme.colorScheme.icon.primary
            )
            Text(
                modifier = Modifier.padding(start = 8.dp).weight(1f),
                text = title,
                style = Theme.typography.heading.extraSmall,
                color = Theme.colorScheme.text.title,
                maxLines = 1,
                overflow = Ellipsis,
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = current,
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.text.body,
            )
        }
        Text(
            modifier = Modifier,
            text = description,
            style = Theme.typography.body.small,
            color = Theme.colorScheme.text.body,
        )
    }
}

@Preview(widthDp = 320)
@Composable
private fun NotificationCardPreview() = SpendooTheme {
    NotificationCard(
        icon = Res.drawable.ic_warning,
        title = "Budget Exceeded!",
        current = "1h",
        description = "You’ve exceeded your shopping budget by 20% this month.\n" +
                "Please review your spending.",
    )
}


