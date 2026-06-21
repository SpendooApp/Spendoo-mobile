package com.aay.compose.lineChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.utils.clickedOnThisPoint
import com.aay.compose.utils.formatToThousandsMillionsBillions

private var lastClickedPoint: Pair<Float, Float>? = null

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawQuarticLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingX: Dp,
    spacingY: Dp,
    specialChart: Boolean,
    clickedPoints: MutableList<Pair<Float, Float>>,
    xRegionWidth: Dp,
    textMeasurer: TextMeasurer,
) {
    val fillPath = Path()
    val solidPath = Path()
    val dashedPath = Path()

    drawLineAsQuadratic(
        line = line,
        fillPath = fillPath,
        solidPath = solidPath,
        dashedPath = dashedPath,
        lowerValue = lowerValue,
        upperValue = upperValue,
        animatedProgress = animatedProgress,
        spacingY = spacingY,
        specialChart = specialChart,
        clickedPoints = clickedPoints,
        textMeasurer = textMeasurer,
        xRegionWidth = xRegionWidth
    )

    if (line.lineShadow && !specialChart) {
        val shadowPath = Path().apply {
            addPath(fillPath)
            lineTo(size.width - xRegionWidth.toPx() + 40.dp.toPx(), size.height * 40)
            lineTo(spacingX.toPx() * 2, size.height * 40)
            close()
        }
        clipRect(right = size.width * animatedProgress.value) {
            drawPath(
                path = shadowPath, brush = Brush.verticalGradient(
                    colors = listOf(
                        line.lineColor.copy(alpha = .3f), Color.Transparent
                    ), endY = (size.height.toDp() - spacingY).toPx()
                )
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawLineAsQuadratic(
    line: LineParameters,
    fillPath: Path,
    solidPath: Path,
    dashedPath: Path,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingY: Dp,
    specialChart: Boolean,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasurer: TextMeasurer,
    xRegionWidth: Dp
) {
    var medX: Float
    val height = size.height.toDp()

    drawPathLineWrapper(
        lineParameter = line,
        fillPath = fillPath,
        solidPath = solidPath,
        dashedPath = dashedPath,
        animatedProgress = animatedProgress,
    ) { lineParameter, index ->

        val yTextLayoutResult = textMeasurer.measure(
            text = AnnotatedString(upperValue.formatToThousandsMillionsBillions()),
        ).size.width
        val textSpace = yTextLayoutResult - (yTextLayoutResult/4)

        val info = lineParameter.data[index]
        val nextInfo = lineParameter.data.getOrNull(index + 1) ?: lineParameter.data.last()
        val firstRatio = (info - lowerValue) / (upperValue - lowerValue)
        val secondRatio = (nextInfo - lowerValue) / (upperValue - lowerValue)

        val xFirstPoint = (textSpace * 1.5.toFloat().toDp()) + index * xRegionWidth
        val xSecondPoint =
            (textSpace * 1.5.toFloat().toDp()) + (index + checkLastIndex(
                lineParameter.data,
                index
            )) * xRegionWidth

        val yFirstPoint = (height.toPx()
                + 11.dp.toPx()
                - spacingY.toPx()
                - (firstRatio * (size.height.toDp() - spacingY).toPx())
                )
        val ySecondPoint = (height.toPx()
                + 11.dp.toPx()
                - spacingY.toPx()
                - (secondRatio * (size.height.toDp() - spacingY).toPx())
                )

        val tolerance = 20.dp.toPx()
        val savedClicks =
            clickedOnThisPoint(clickedPoints, xFirstPoint.toPx(), yFirstPoint, tolerance)
        if (savedClicks) {
            if (lastClickedPoint != null) {
                clickedPoints.clear()
                lastClickedPoint = null
            } else {
                lastClickedPoint = Pair(xFirstPoint.toPx(), yFirstPoint.toFloat())
                circleWithRectAndText(
                    x = xFirstPoint,
                    y = yFirstPoint,
                    textMeasure = textMeasurer,
                    info = info,
                    stroke = Stroke(width = 2.dp.toPx()),
                    line = line,
                    animatedProgress = animatedProgress
                )
            }

        }

        if (line.highlightedPoints?.contains(index) == true) {
            circleWithRectAndText(
                x = xFirstPoint,
                y = yFirstPoint,
                textMeasure = textMeasurer,
                info = info,
                stroke = Stroke(width = 2.dp.toPx()),
                line = line,
                animatedProgress = animatedProgress
            )
        }

        if (index == 0) {
            fillPath.moveTo(xFirstPoint.toPx(), yFirstPoint.toFloat())
            medX = ((xFirstPoint + xSecondPoint) / 2f).toPx()
            fillPath.cubicTo(
                medX,
                yFirstPoint.toFloat(),
                medX,
                ySecondPoint.toFloat(),
                xSecondPoint.toPx(),
                ySecondPoint.toFloat()
            )
        } else {
            medX = ((xFirstPoint + xSecondPoint) / 2f).toPx()
            fillPath.cubicTo(
                medX,
                yFirstPoint.toFloat(),
                medX,
                ySecondPoint.toFloat(),
                xSecondPoint.toPx(),
                ySecondPoint.toFloat()
            )
        }

        val isDashed = line.dashedRanges?.any { range ->
            index in range && (index + 1) in range
        } ?: false

        val segmentLength = checkLastIndex(lineParameter.data, index)
        if (segmentLength > 0) {
            medX = ((xFirstPoint + xSecondPoint) / 2f).toPx()
            if (isDashed) {
                dashedPath.moveTo(xFirstPoint.toPx(), yFirstPoint.toFloat())
                dashedPath.cubicTo(
                    medX,
                    yFirstPoint.toFloat(),
                    medX,
                    ySecondPoint.toFloat(),
                    xSecondPoint.toPx(),
                    ySecondPoint.toFloat()
                )
            } else {
                solidPath.moveTo(xFirstPoint.toPx(), yFirstPoint.toFloat())
                solidPath.cubicTo(
                    medX,
                    yFirstPoint.toFloat(),
                    medX,
                    ySecondPoint.toFloat(),
                    xSecondPoint.toPx(),
                    ySecondPoint.toFloat()
                )
            }
        }

        if (index == 0 && specialChart) {
            chartCircle(
                xFirstPoint.toPx(),
                yFirstPoint.toFloat(),
                color = lineParameter.lineColor,
                animatedProgress = animatedProgress,
            )
        }
    }
}

private fun checkLastIndex(data: List<Double>, index: Int): Int {
    return if (data[index] == data[data.lastIndex])
        0
    else
        1
}
