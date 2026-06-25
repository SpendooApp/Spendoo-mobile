package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.SpendooPreview
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.asString
import com.spendoo.statistics.presentation.screen.statistics.LineChartUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.budget
import spendoo.designsystem.generated.resources.income
import spendoo.designsystem.generated.resources.spent

@Composable
fun LineChartSection(
    lineChartUiState: LineChartUiState,
    animateChart: Boolean,
    modifier: Modifier = Modifier,
    pointsGap: Dp? = null,
) {
    val lines = listOf(
        LineParameters(
            label = stringResource(Res.string.budget),
            data = lineChartUiState.budgetData,
            lineColor = Theme.colorScheme.additional.onWarning,
            lineType = LineType.CURVED_LINE,
            lineShadow = false
        ),
        LineParameters(
            label = stringResource(Res.string.spent),
            data = lineChartUiState.spentData,
            lineColor = Theme.colorScheme.icon.primary,
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            highlightedPoints = listOf(lineChartUiState.highestSpendingBucketIndex),
            dashedRanges = lineChartUiState.dashedRanges,
            tooltipLabel = null
        ),
        LineParameters(
            label = stringResource(Res.string.income),
            data = lineChartUiState.incomeData,
            lineColor = Theme.colorScheme.additional.onSuccess,
            lineType = LineType.CURVED_LINE,
            lineShadow = false
        )
    )

    Column(modifier = modifier) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(horizontal = 16.dp),
            linesParameters = lines,
            xAxisData = lineChartUiState.xAxisLabels.map { it.asString() },
            animateChart = animateChart,
            yAxisStyle = Theme.typography.label.medium.extraSmall.copy(color = Theme.colorScheme.text.body),
            xAxisStyle = Theme.typography.label.medium.extraSmall.copy(color = Theme.colorScheme.text.body),
            descriptionStyle = Theme.typography.label.medium.small.copy(color = Theme.colorScheme.brand.secondaryVariant),
            legendPosition = LegendPosition.DISAPPEAR,
            pointsGap = pointsGap,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(
                color = Theme.colorScheme.additional.onWarning,
                label = stringResource(Res.string.budget)
            )
            Spacer(modifier = Modifier.width(16.dp))
            LegendItem(
                color = Theme.colorScheme.icon.primary,
                label = stringResource(Res.string.spent)
            )
            Spacer(modifier = Modifier.width(16.dp))
            LegendItem(
                color = Theme.colorScheme.additional.onSuccess,
                label = stringResource(Res.string.income)
            )
        }
    }
}

@Composable
@Preview(name = "Light", widthDp = 560)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL, widthDp = 560)
fun LineChartSectionPreview() = SpendooPreview {
    LineChartSection(
        lineChartUiState = LineChartUiState(
            budgetData = listOf(9000.0, 19000.0, 17000.0),
            spentData = listOf(5000.0, 0.0, 12000.0),
            incomeData = listOf(9000.0, 17000.0, 15000.0),
            xAxisLabels = listOf(UiText.DynamicString("Jan"), UiText.DynamicString("Feb"), UiText.DynamicString("Mar")),
            highestSpendingBucketIndex = 2,
            dashedRanges = emptyList()
        ),
        animateChart = false,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Theme.colorScheme.background.primary)
    )
}