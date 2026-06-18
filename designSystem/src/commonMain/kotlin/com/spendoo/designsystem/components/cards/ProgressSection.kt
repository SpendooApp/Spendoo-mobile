package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_money
import spendoo.designsystem.generated.resources.ic_slash

@Composable
fun ProgressSection(
    percentage: Int,
    total: Int,
    current: Int,
    progressColor: Color = if (percentage < 100) Theme.colorScheme.icon.primary else Theme.colorScheme.additional.onError,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "$current",
                color = Theme.colorScheme.text.body,
                style = Theme.typography.body.medium,
            )
            Icon(
                modifier = Modifier.size(8.dp, 24.dp),
                painter = Res.drawable.ic_slash.painter(),
                contentDescription = null,
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp)
                    .weight(1f),
                text = "$total",
                color = Theme.colorScheme.text.body,
                style = Theme.typography.body.medium,
            )
            Text(
                style = Theme.typography.label.medium.medium,
                color = Theme.colorScheme.icon.primary,
                text = "$percentage%"
            )
        }

        LinearProgressIndicator(
            progress = {
                percentage / 100f
            },
            modifier = Modifier.fillMaxWidth().height(12.dp),
            gapSize = (-10).dp,
            drawStopIndicator = {},
            color = progressColor,
            trackColor = Theme.colorScheme.button.secondary
        )
    }
}