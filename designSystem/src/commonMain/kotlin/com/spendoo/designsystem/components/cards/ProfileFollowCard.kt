package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun ProfileFollowCard(
    imageUrl: String,
    name: String,
    actions: List<@Composable RowScope.() -> Unit>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(16.dp),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = shape)
            .border(color = Theme.colorScheme.border.primary, width = 0.5.dp, shape = shape)
            .padding(8.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile Image",
            modifier = Modifier.size(48.dp).clip(CircleShape),
        )
        Text(
            modifier = modifier.padding(start = 8.dp).weight(1f),
            text = name,
            style = Theme.typography.label.medium.medium,
            color = Theme.colorScheme.text.titleSmall,
            maxLines = 1,
            overflow = Ellipsis
        )
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            actions.forEach {
                it()
            }
        }
    }
}

@Composable
fun ActionButton(
    onActionClick: () -> Unit,
    text: String,
    textColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(16.dp, 8.dp)
            .clickableNoRipple { onActionClick() },
        text = text,
        style = Theme.typography.label.medium.small,
        color = textColor

    )
}

@Preview(widthDp = 320)
@Composable
private fun ProfileFollowCardPreview() {
    SpendooTheme {
        ProfileFollowCard(
            imageUrl = "",
            name = "Nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
            actions = listOf(
                { ActionButton(
                    onActionClick = { },
                    text = "Approve",
                    textColor = Theme.colorScheme.button.primary,
                    backgroundColor = Theme.colorScheme.button.secondary,
                ) },
                { ActionButton(
                    onActionClick = { },
                    text = "Approve",
                    textColor = Theme.colorScheme.button.primary,
                    backgroundColor = Theme.colorScheme.button.secondary,
                ) }
        )
        )
    }
}
