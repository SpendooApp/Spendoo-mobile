package com.aay.compose.barChart.components


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.baseChartContainer
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.xAxisDrawing
import com.aay.compose.utils.ChartDefaultValues.specialChart
import com.aay.compose.utils.checkIfDataValid
import com.aay.compose.utils.formatToThousandsMillionsBillions
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun BarChartContent(
    barsParameters: List<BarParameters>,
    gridColor: Color,
    xAxisData: List<String>,
    isShowGrid: Boolean,
    animateChart: Boolean,
    showGridWithSpacer: Boolean,
    yAxisStyle: TextStyle,
    xAxisStyle: TextStyle,
    backgroundLineWidth: Float,
    yAxisRange: Int,
    showXAxis: Boolean,
    showYAxis: Boolean,
    barWidth: Dp,
    spaceBetweenBars: Dp,
    spaceBetweenGroups: Dp,
    modifier: Modifier = Modifier,
    barCornerRadius: Dp
) {

    val textMeasure = rememberTextMeasurer()

    val animatedProgress = remember(barsParameters) {
        if (animateChart) Animatable(0f) else Animatable(1f)
    }
    var upperValue by rememberSaveable {
        mutableStateOf(barsParameters.getUpperValue())
    }
    var lowerValue by rememberSaveable {
        mutableStateOf(barsParameters.getLowerValue())
    }
    val density = LocalDensity.current

    val yTextLayoutResultDp = with(density) {
        textMeasure.measure(
            text = AnnotatedString(upperValue.toFloat().formatToThousandsMillionsBillions()),
            style = yAxisStyle
        ).size.width.toDp()
    }

    checkIfDataValid(xAxisData = xAxisData, barParameters = barsParameters)
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val boxHeight = maxHeight

        val spacingY = (boxHeight / 10) + 10.dp
        val xRegionWidth = ((barWidth + spaceBetweenBars) * barsParameters.size) + spaceBetweenGroups
        val xRegionWidthWithoutSpacing = xRegionWidth - spaceBetweenGroups
        val lastLabelWidthDp = with(density) {
            if (xAxisData.isNotEmpty()) {
                textMeasure.measure(
                    text = AnnotatedString(xAxisData.last().toString()),
                    style = xAxisStyle
                ).size.width.toDp()
            } else {
                0.dp
            }
        }
        val chartMaxWidth = (xRegionWidth * xAxisData.size) - spaceBetweenGroups + lastLabelWidthDp + 16.dp
        val chartMaxHeight = boxHeight - spacingY

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            baseChartContainer(
                xAxisData = xAxisData,
                textMeasure = textMeasure,
                upperValue = upperValue.toFloat(),
                lowerValue = lowerValue.toFloat(),
                isShowGrid = isShowGrid,
                backgroundLineWidth = backgroundLineWidth,
                gridColor = gridColor,
                showGridWithSpacer = showGridWithSpacer,
                spacingY = spacingY,
                yAxisStyle = yAxisStyle,
                xAxisStyle = xAxisStyle,
                yAxisRange = yAxisRange,
                showXAxis = showXAxis,
                showYAxis = showYAxis,
                isFromBarChart = true,
                xRegionWidth = xRegionWidth
            )
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(start = yTextLayoutResultDp + (yTextLayoutResultDp / 2))
                .horizontalScroll(rememberScrollState())
        ) {

            Canvas(
                Modifier.width(chartMaxWidth).fillMaxHeight()
            ) {
                drawBarGroups(
                    barsParameters = barsParameters,
                    upperValue = upperValue,
                    barWidth = barWidth,
                    xRegionWidth = xRegionWidth,
                    spaceBetweenBars = spaceBetweenBars,
                    maxWidth = chartMaxWidth,
                    height = chartMaxHeight,
                    animatedProgress = animatedProgress,
                    barCornerRadius = barCornerRadius
                )

                xAxisDrawing(
                    xAxisData = xAxisData,
                    textMeasure = textMeasure,
                    xAxisStyle = xAxisStyle,
                    specialChart = specialChart,
                    xRegionWidth = xRegionWidth,
                    xRegionWidthWithoutSpacing = xRegionWidthWithoutSpacing,
                    height = chartMaxHeight,
                )
            }
        }
    }


    LaunchedEffect(barsParameters, animateChart) {
        upperValue = barsParameters.getUpperValue()
        lowerValue = barsParameters.getLowerValue()
        if (animateChart) {
            delay(400.milliseconds)
            animatedProgress.animateTo(
                targetValue = 1f, animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }
}

private fun List<BarParameters>.getUpperValue(): Double {
    return this.flatMap { item -> item.data }.maxOrNull()?.plus(1.0) ?: 0.0
}

private fun List<BarParameters>.getLowerValue(): Double {
    return this.flatMap { item -> item.data }.minOrNull() ?: 0.0
}

