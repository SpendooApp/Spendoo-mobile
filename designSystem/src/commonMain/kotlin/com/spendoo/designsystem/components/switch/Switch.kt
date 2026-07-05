package com.spendoo.designsystem.components.switch

import androidx.compose.material3.Switch as Material3Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spendoo.designsystem.theme.theme.Theme

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
            checkedThumbColor = Theme.colorScheme.brand.primary,
            checkedTrackColor = Theme.colorScheme.button.secondary,
            uncheckedThumbColor = Theme.colorScheme.text.body,
            uncheckedTrackColor = Theme.colorScheme.background.secondary
        )
    )
}
