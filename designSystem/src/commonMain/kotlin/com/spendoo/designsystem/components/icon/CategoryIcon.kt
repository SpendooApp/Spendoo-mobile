package com.spendoo.designsystem.components.icon

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_drink

@Composable
fun CategoryIcon(
    icon: DrawableResource,
    iconTint: Color = Theme.colorScheme.icon.primary,
    size: Dp = 40.dp
) {
    SpendooIconButton(
        iconRes = icon,
        contentDescription = null,
        onClick = {},
        size = size,
        shape = RoundedCornerShape(12.dp),
        showBorder = false,
        iconSize = size / 2,
        backgroundColor = Theme.colorScheme.button.secondary,
        tint = iconTint,
    )
}

@Composable
@Preview
fun CategoryIconPreview() {
    SpendooTheme {
        CategoryIcon(icon = Res.drawable.ic_drink)
    }
}