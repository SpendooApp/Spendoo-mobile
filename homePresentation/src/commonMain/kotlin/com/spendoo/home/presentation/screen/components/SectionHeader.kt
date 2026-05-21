package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.view_all

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    onViewAll: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Theme.typography.title.medium,
            color = Theme.colorScheme.text.title
        )
        Text(
            text = stringResource(Res.string.view_all),
            style = Theme.typography.label.medium.medium,
            color = Theme.colorScheme.button.primary,
            modifier = Modifier.clickableNoRipple { onViewAll() }
        )
    }
}
