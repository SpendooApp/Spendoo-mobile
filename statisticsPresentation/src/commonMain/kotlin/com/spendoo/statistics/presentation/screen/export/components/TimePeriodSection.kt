package com.spendoo.statistics.presentation.screen.export.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonSize
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.statistics.domain.entity.TimePeriod
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.custom_range
import spendoo.designsystem.generated.resources.last_month
import spendoo.designsystem.generated.resources.time_period

@Composable
fun TimePeriodSection(
    selectedTimePeriod: TimePeriod,
    onTimePeriodSelected: (TimePeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.time_period),
            style = Theme.typography.heading.tiny,
            color = Theme.colorScheme.text.body
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppButton(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.last_month),
                onClick = { onTimePeriodSelected(TimePeriod.LAST_MONTH) },
                size = AppButtonSize.Small,
                type = if (selectedTimePeriod == TimePeriod.LAST_MONTH) AppButtonType.Primary else AppButtonType.Secondary,
            )

            AppButton(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.custom_range),
                onClick = { onTimePeriodSelected(TimePeriod.CUSTOM_RANGE) },
                size = AppButtonSize.Small,
                type = if (selectedTimePeriod == TimePeriod.CUSTOM_RANGE) AppButtonType.Primary else AppButtonType.Secondary,
            )
        }
    }
}

@Composable
@Preview
private fun TimePeriodSectionPreview() = SpendooTheme {
    TimePeriodSection(
        selectedTimePeriod = TimePeriod.LAST_MONTH,
        onTimePeriodSelected = {}
    )
}