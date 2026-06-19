package com.spendoo.designsystem.components.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_clock_red

@Composable
fun TimeLeftBadge(
    timeLeft: String,
    isDueSoon: Boolean
) {
    Row(
        modifier = Modifier
            .background(
                if (isDueSoon) Theme.colorScheme.additional.error else Theme.colorScheme.border.primary,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            painter = Res.drawable.ic_clock_red.painter(),
            contentDescription = null,
            tint = if (isDueSoon) Theme.colorScheme.additional.onError else Theme.colorScheme.button.onSecondary,
        )

        Text(
            modifier = Modifier.padding(start = 9.dp),
            text = timeLeft,
            style = Theme.typography.label.medium.extraSmall,
            color = if (isDueSoon) Theme.colorScheme.additional.onError else Theme.colorScheme.button.onSecondary
        )
    }
}