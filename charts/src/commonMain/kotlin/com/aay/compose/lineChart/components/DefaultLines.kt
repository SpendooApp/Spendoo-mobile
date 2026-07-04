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
internal fun DrawScope.drawDefaultLineWithShadow(
    line: LineParameters,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingX: Dp,
    spacingY: Dp,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasure: TextMeasurer,
    xRegionWidth: Dp,
) {
    val fillPath = Path()
    val solidPath = Path()
    val dashedPath = Path()

    drawLineAsDefault(
        lineParameter = line,
        fillPath = fillPath,
        solidPath = solidPath,
        dashedPath = dashedPath,
        lowerValue = lowerValue,
        upperValue = upperValue,
        animatedProgress = animatedProgress,
        spacingY = spacingY,
        clickedPoints = clickedPoints,
        textMeasure = textMeasure,
        xRegionWidth = xRegionWidth
    )

    if (line.lineShadow) {
        val shadowPath = Path().apply {
            addPath(fillPath)
            lineTo(size.width - xRegionWidth.toPx() + 40.dp.toPx(), size.height * 40)
            lineTo(spacingX.toPx() * 2, size.height * 40)
            close()
        }
        clipRect(right = size.width * animatedProgress.value) {
            drawPath(
                path = shadowPath, brush = Brush.verticalGradient(
                    colors = listOf(line.lineColor.copy(alpha = .3f), Color.Transparent),
                    endY = (size.height.toDp() - spacingY).toPx()
                )
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawLineAsDefault(
    lineParameter: LineParameters,
    fillPath: Path,
    solidPath: Path,
    dashedPath: Path,
    lowerValue: Float,
    upperValue: Float,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    spacingY: Dp,
    clickedPoints: MutableList<Pair<Float, Float>>,
    textMeasure: TextMeasurer,
    xRegionWidth: Dp,
) {
    val height = size.height.toDp()
    var prevX = 0f
    var prevY = 0f

    drawPathLineWrapper(
        lineParameter = lineParameter,
        fillPath = fillPath,
        solidPath = solidPath,
        dashedPath = dashedPath,
        animatedProgress = animatedProgress,
    ) { lineParameter, index ->

        val yTextLayoutResult = textMeasure.measure(
            text = AnnotatedString(upperValue.formatToThousandsMillionsBillions()),
        ).size.width
        val textSpace = yTextLayoutResult - (yTextLayoutResult/4)
        val info = lineParameter.data[index]
        val ratio = (info - lowerValue) / (upperValue - lowerValue)
        val startXPoint = (textSpace * 1.5.toFloat().toDp()) + (index * xRegionWidth)
        val startYPoint =
            (height.toPx() + 8.dp.toPx() - spacingY.toPx() - (ratio * (height.toPx() - spacingY.toPx())))

        val tolerance = 20.dp.toPx()
        val savedClicks =
            clickedOnThisPoint(clickedPoints, startXPoint.toPx(), startYPoint, tolerance)

        if (savedClicks) {
            if (lastClickedPoint != null) {
                clickedPoints.clear()
                lastClickedPoint = null
            } else {
                lastClickedPoint = Pair(startXPoint.toPx(), startYPoint.toFloat())
                circleWithRectAndText(
                    x = startXPoint,
                    y = startYPoint,
                    textMeasure = textMeasure,
                    info = info,
                    stroke = Stroke(width = 2.dp.toPx()),
                    line = lineParameter,
                    animatedProgress = animatedProgress
                )
            }
        }

        if (lineParameter.highlightedPoints?.contains(index) == true) {
            circleWithRectAndText(
                x = startXPoint,
                y = startYPoint,
                textMeasure = textMeasure,
                info = info,
                stroke = Stroke(width = 2.dp.toPx()),
                line = lineParameter,
                animatedProgress = animatedProgress
            )
        }

        val currentX = startXPoint.toPx()
        val currentY = startYPoint.toFloat()

        if (index == 0) {
            fillPath.moveTo(currentX, currentY)
        } else {
            fillPath.lineTo(currentX, currentY)

            val isDashed = lineParameter.dashedRanges?.any { range ->
                index in range && (index - 1) in range
            } ?: false

            if (isDashed) {
                dashedPath.moveTo(prevX, prevY)
                dashedPath.lineTo(currentX, currentY)
            } else {
                solidPath.moveTo(prevX, prevY)
                solidPath.lineTo(currentX, currentY)
            }
        }

        prevX = currentX
        prevY = currentY
    }
}

