package com.aay.compose.lineChart


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.aay.compose.baseComponents.baseChartContainer
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.components.drawDefaultLineWithShadow
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import com.aay.compose.utils.checkIfDataValid
import com.aay.compose.utils.formatToThousandsMillionsBillions
import com.aay.compose.baseComponents.xAxisDrawing
import com.aay.compose.lineChart.components.drawQuarticLineWithShadow
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun ChartContent(
    modifier: Modifier,
    linesParameters: List<LineParameters>,
    gridColor: Color,
    xAxisData: List<String>,
    isShowGrid: Boolean,
    barWidthPx: Dp,
    animateChart: Boolean,
    showGridWithSpacer: Boolean,
    yAxisStyle: TextStyle,
    xAxisStyle: TextStyle,
    yAxisRange: Int,
    showXAxis: Boolean,
    showYAxis: Boolean,
    specialChart: Boolean,
    onChartClick: (Float, Float) -> Unit,
    clickedPoints: MutableList<Pair<Float, Float>>,
    gridOrientation: GridOrientation,
    pointsGap: Dp?,
) {

    val textMeasure = rememberTextMeasurer()

    val animatedProgress = remember {
        if (animateChart) Animatable(0f) else Animatable(1f)
    }
    var upperValue by rememberSaveable {
        mutableStateOf(linesParameters.getUpperValue())
    }
    var lowerValue by rememberSaveable {
        mutableStateOf(linesParameters.getLowerValue())
    }
    checkIfDataValid(xAxisData = xAxisData, linesParameters = linesParameters)

    val density = LocalDensity.current

    val yTextLayoutResultDp = with(density) {
        textMeasure.measure(
            text = AnnotatedString(upperValue.toFloat().formatToThousandsMillionsBillions()),
            style = yAxisStyle
        ).size.width.toDp()
    }
    val textSpace = yTextLayoutResultDp - (yTextLayoutResultDp / 4)

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val boxWidth = maxWidth
        val boxHeight = maxHeight

        val spacingY = (boxHeight / 8)
        val textLayoutResult = with(density) { textMeasure.measure(
            text = AnnotatedString(xAxisData.last().toString()),
            style = xAxisStyle
        ).size.width.toDp() }

        val rightPadding = textLayoutResult / 2 + 16.dp
        val leftPadding = textSpace * 1.5f
        val availableWidth = boxWidth - leftPadding - rightPadding
        val xRegionWidth = pointsGap ?: (availableWidth / (xAxisData.size - 1).coerceAtLeast(1).toFloat())

        val chartMaxWidth = if (pointsGap != null) {
            (xRegionWidth * (xAxisData.size - 1)) + (textSpace * 2.5f) + textLayoutResult
        } else {
            boxWidth
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            baseChartContainer(
                xAxisData = xAxisData,
                textMeasure = textMeasure,
                upperValue = upperValue.toFloat(),
                lowerValue = lowerValue.toFloat(),
                isShowGrid = isShowGrid,
                backgroundLineWidth = barWidthPx.toPx(),
                gridColor = gridColor,
                showGridWithSpacer = showGridWithSpacer,
                spacingY = spacingY,
                yAxisStyle = yAxisStyle,
                xAxisStyle = xAxisStyle,
                yAxisRange = yAxisRange,
                showXAxis = showXAxis,
                showYAxis = showYAxis,
                specialChart = specialChart,
                isFromBarChart = true, // We draw X axis in the scrollable canvas
                gridOrientation = gridOrientation,
                xRegionWidth = xRegionWidth
            )
        }
        
        Box(
            modifier = Modifier.fillMaxSize().padding(start = yTextLayoutResultDp + (yTextLayoutResultDp / 2))
                .horizontalScroll(rememberScrollState())
        ) {
            Canvas(
                modifier = Modifier
                    .width(chartMaxWidth)
                    .fillMaxHeight()
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            onChartClick(offset.x, offset.y)
                        }
                    }
            ) {
                val spacingX = (size.width / 50.dp.toPx()).dp

                if (showXAxis && !specialChart) {
                    xAxisDrawing(
                        xAxisData = xAxisData,
                        textMeasure = textMeasure,
                        xAxisStyle = xAxisStyle,
                        specialChart = specialChart,
                        upperValue = upperValue.toFloat(),
                        xRegionWidth = xRegionWidth
                    )
                }

                if (specialChart) {
                    if (linesParameters.size >= 2) {
                        throw Exception("Special case must contain just one line")
                    }
                    linesParameters.forEach { line ->
                        drawQuarticLineWithShadow(
                            line = line,
                            lowerValue = lowerValue.toFloat(),
                            upperValue = upperValue.toFloat(),
                            animatedProgress = animatedProgress,
                            spacingX = spacingX,
                            spacingY = spacingY,
                            specialChart = specialChart,
                            clickedPoints = clickedPoints,
                            xRegionWidth = xRegionWidth,
                            textMeasurer = textMeasure
                        )
                    }
                } else {
                    if (linesParameters.size >= 2) {
                        clickedPoints.clear()
                    }
                    linesParameters.forEach { line ->
                        if (line.lineType == LineType.DEFAULT_LINE) {
                            drawDefaultLineWithShadow(
                                line = line,
                                lowerValue = lowerValue.toFloat(),
                                upperValue = upperValue.toFloat(),
                                animatedProgress = animatedProgress,
                                spacingX = spacingX,
                                spacingY = spacingY,
                                clickedPoints = clickedPoints,
                                textMeasure = textMeasure,
                                xRegionWidth = xRegionWidth
                            )
                        } else {
                            drawQuarticLineWithShadow(
                                line = line,
                                lowerValue = lowerValue.toFloat(),
                                upperValue = upperValue.toFloat(),
                                animatedProgress = animatedProgress,
                                spacingX = spacingX,
                                spacingY = spacingY,
                                specialChart = specialChart,
                                clickedPoints = clickedPoints,
                                xRegionWidth = xRegionWidth,
                                textMeasurer = textMeasure
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(linesParameters, animateChart) {
        upperValue = linesParameters.getUpperValue()
        lowerValue = linesParameters.getLowerValue()
        if (animateChart) {
            delay(400.milliseconds)
            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
        }
    }
}

private fun List<LineParameters>.getUpperValue(): Double {
    return this.flatMap { item -> item.data }.maxOrNull()?.plus(1.0) ?: 0.0
}

private fun List<LineParameters>.getLowerValue(): Double {
    return this.flatMap { item -> item.data }.minOrNull() ?: 0.0
}

