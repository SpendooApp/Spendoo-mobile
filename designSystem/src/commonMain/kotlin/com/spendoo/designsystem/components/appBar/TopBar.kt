package com.spendoo.designsystem.components.appBar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_arrow_left

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClicked: (() -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    actions: List<@Composable RowScope.() -> Unit> = emptyList(),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBackClicked?.let { onBackClicked ->
            TopBarIcon(
                iconRes = Res.drawable.ic_arrow_left,
                contentDescription = "Back",
                onClick = onBackClicked
            )
        }

        leading?.invoke()

        Text(
            text = title,
            style = Theme.typography.title.large,
            color = Theme.colorScheme.text.title
        )
        if (actions.isNotEmpty()) {
            Spacer(modifier = Modifier.weight(1f))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                actions.forEach { action ->
                    action()
                }
            }
        }
    }
}

@Composable
fun TopBarIcon(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource,
    shape: Shape = RoundedCornerShape(16.dp),
    contentDescription: String?,
    tint: Color = Theme.colorScheme.text.label,
    iconSize: Dp = 14.dp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(shape)
            .border(1.dp, Theme.colorScheme.border.secondary, shape)
            .clickableNoRipple(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = iconRes.painter(),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}