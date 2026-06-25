package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun LegendItem(
    color: Color,
    label: String,
    labelColor: Color = Theme.colorScheme.text.body,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            style = Theme.typography.label.medium.small,
            color = labelColor,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun LegendItemPreview() {
    SpendooTheme {
        LegendItem(
            color = Theme.colorScheme.additional.success,
            label = "Within budget",
            labelColor = Theme.colorScheme.brand.secondaryVariant          
        )
    }
}
