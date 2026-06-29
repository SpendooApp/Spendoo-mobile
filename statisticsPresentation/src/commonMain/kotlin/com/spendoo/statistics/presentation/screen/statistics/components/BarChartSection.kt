package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.aay.compose.barChart.BarChart
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.model.LegendPosition
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.asString
import com.spendoo.statistics.domain.entity.BudgetStatus
import com.spendoo.statistics.presentation.screen.statistics.BarChartBucketUiState
import com.spendoo.statistics.presentation.screen.statistics.BarChartUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.last_six_periods
import spendoo.designsystem.generated.resources.overspending
import spendoo.designsystem.generated.resources.risk
import spendoo.designsystem.generated.resources.spent
import spendoo.designsystem.generated.resources.within

@Composable
fun BarChartSection(
    barChartUiState: BarChartUiState,
    animateChart: Boolean,
    modifier: Modifier = Modifier
) {
    val barColors = barChartUiState.buckets.map { bucket ->
        when (bucket.status) {
            BudgetStatus.WITHIN -> Theme.colorScheme.additional.onSuccess
            BudgetStatus.RISK -> Theme.colorScheme.additional.onWarning
            BudgetStatus.OVERSPEND -> Theme.colorScheme.icon.primary
        }
    }
    val chartParameters = listOf(
        BarParameters(
            dataName = stringResource(Res.string.spent),
            data = barChartUiState.buckets.map { it.spending },
            barColor = Theme.colorScheme.brand.primary,
            barColors = barColors
        )
    )

    Column(modifier) {
        Text(
            text = stringResource(Res.string.last_six_periods),
            style = Theme.typography.heading.tiny,
            color = Theme.colorScheme.text.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp)
        ) {
            BarChart(
                chartParameters = chartParameters,
                xAxisData = barChartUiState.xAxisLabels.map { it.asString() },
                animateChart = animateChart,
                legendPosition = LegendPosition.DISAPPEAR,
                barWidth = 12.dp,
                barCornerRadius = 100.dp,
                spaceBetweenGroups = 30.dp,
                yAxisStyle = Theme.typography.label.medium.extraSmall.copy(color = Theme.colorScheme.text.body),
                xAxisStyle = Theme.typography.label.medium.extraSmall.copy(color = Theme.colorScheme.text.body),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(
                color = Theme.colorScheme.additional.onSuccess,
                label = stringResource(Res.string.within)
            )
            Spacer(modifier = Modifier.width(16.dp))
            LegendItem(
                color = Theme.colorScheme.additional.onWarning,
                label = stringResource(Res.string.risk)
            )
            Spacer(modifier = Modifier.width(16.dp))
            LegendItem(
                color = Theme.colorScheme.icon.primary,
                label = stringResource(Res.string.overspending)
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun BarChartSectionPreview() = SpendooTheme {
    BarChartSection(
        barChartUiState = BarChartUiState(
            buckets = listOf(
                BarChartBucketUiState(1000.0, BudgetStatus.WITHIN),
                BarChartBucketUiState(1500.0, BudgetStatus.RISK),
                BarChartBucketUiState(2000.0, BudgetStatus.OVERSPEND)
            ),
            xAxisLabels = listOf(UiText.DynamicString("Jan"), UiText.DynamicString("Feb"), UiText.DynamicString("Mar"))
        ),
        animateChart = false,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Theme.colorScheme.background.primary)
    )
}