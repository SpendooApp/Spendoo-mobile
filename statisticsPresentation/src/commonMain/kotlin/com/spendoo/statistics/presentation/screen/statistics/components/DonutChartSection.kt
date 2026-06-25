package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.model.PieChartData
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.designsystem.utils.formatMoneyAbbreviated
import com.spendoo.designsystem.utils.asString
import com.spendoo.statistics.domain.entity.TopCategories
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.presentation.screen.statistics.PieChartUiState
import com.spendoo.statistics.presentation.screen.statistics.toExpensesTitleRes
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.expenses
import spendoo.designsystem.generated.resources.ic_money
import spendoo.designsystem.generated.resources.total

@Composable
fun DonutChartSection(
    pieChartUiStates: List<PieChartUiState>,
    animateChart: Boolean,
    topCategories: TopCategories,
    selectedGranularity: Granularity,
    modifier: Modifier = Modifier
) {
    val categoryColors = listOf(
        Theme.colorScheme.additional.onError,
        Theme.colorScheme.additional.onWarning,
        Theme.colorScheme.additional.purple,
        Theme.colorScheme.primary.variant700,
        Theme.colorScheme.icon.primary,
        Theme.colorScheme.additional.onSuccess
    )
    val pieChartData = pieChartUiStates.mapIndexed { index, category ->
        PieChartData(
            data = category.spending,
            color = categoryColors.getOrElse(index) { Theme.colorScheme.brand.primary },
            partName = category.categoryName,
        )
    }

    Column(modifier) {
        Text(
            text = stringResource(selectedGranularity.toExpensesTitleRes()),
            style = Theme.typography.heading.small,
            color = Theme.colorScheme.text.title,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1.2f)
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                DonutChart(
                    modifier = Modifier.fillMaxSize(),
                    pieChartData = pieChartData,
                    outerCircularColor = Color.Transparent,
                    innerCircularColor = Color.Transparent,
                    ratioLineColor = Theme.colorScheme.text.title,
                    legendPosition = LegendPosition.DISAPPEAR,
                    animateChart = animateChart,
                    donutThickness = 20.dp,
                    showRatioLines = false
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = Res.drawable.ic_money.painter(),
                            contentDescription = null,
                            tint = Theme.colorScheme.text.title
                        )
                        Text(
                            text = formatMoneyAbbreviated(topCategories.totalSpending).asString(),
                            style = Theme.typography.heading.small,
                            color = Theme.colorScheme.text.title
                        )
                    }
                    Text(
                        text = stringResource(Res.string.total),
                        style = Theme.typography.label.medium.small,
                        color = Theme.colorScheme.brand.secondaryVariant
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pieChartData.forEach { data ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(data.color)
                        )
                        Text(
                            text = data.partName,
                            style = Theme.typography.label.medium.medium,
                            color = Theme.colorScheme.text.body,
                            modifier = Modifier.padding(start = 8.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
fun DonutChartSectionPreview() {
    SpendooTheme {
        DonutChartSection(
            modifier = Modifier.background(Theme.colorScheme.background.primary),
            animateChart = false,
            pieChartUiStates = listOf(
                PieChartUiState(200.0, "Food"),
                PieChartUiState(150.0, "Transport"),
                PieChartUiState(100.0, "Entertainment")
            ),
            topCategories = TopCategories(
                totalSpending = 450.0,
                topCategories = emptyList()
            ),
            selectedGranularity = Granularity.MONTH
        )
    }
}
