package com.spendoo.designsystem.components.switch

import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spendoo.designsystem.theme.theme.Theme
import androidx.compose.material3.Switch as Material3Switch

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Material3Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Theme.colorScheme.brand.onPrimary,
            checkedTrackColor = Theme.colorScheme.icon.primary,
            uncheckedThumbColor = Theme.colorScheme.text.body,
            uncheckedTrackColor = Color.Unspecified
        )
    )
}
