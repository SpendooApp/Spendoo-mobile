package com.spendoo.designsystem.components.checkbox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.size.Size
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun CustomRadioButton(
    selected: Boolean,
    size: Dp = 24.dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (selected) Theme.colorScheme.button.primary else Theme.colorScheme.border.secondary
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(width = 2.dp, color = borderColor, shape = CircleShape)
            .clickableNoRipple(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(size/2)
                    .clip(CircleShape)
                    .background(Theme.colorScheme.button.primary)
            )
        }
    }
}

@Composable
@Preview
fun CustomRadioButtonPreview() {
    CustomRadioButton(
        selected = true,
        onClick = {}
    )
}