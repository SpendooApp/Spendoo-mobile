package com.spendoo.designsystem.components.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun CategoryIcon(
    icon: DrawableResource,
    iconTint: Color = Theme.colorScheme.icon.primary
) {
    Box(
        modifier = Modifier.size(40.dp)
            .background(Theme.colorScheme.button.secondary, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    )
    {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = icon.painter(),
            contentDescription = null,
            tint = iconTint,
        )
    }
}