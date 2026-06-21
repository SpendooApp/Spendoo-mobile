package com.aay.compose.lineChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.utils.formatToThousandsMillionsBillions

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.circleWithRectAndText(
    animatedProgress: Animatable<Float, AnimationVector1D>,
    textMeasure: TextMeasurer,
    info: Double,
    stroke: Stroke,
    line: LineParameters,
    x: Dp,
    y: Double,
) {
    chartCircle(x.toPx(), y.toFloat(), line.lineColor, animatedProgress, stroke)
    if (line.tooltipLabel != null) {
        chartRectangleWithText(x, y, textMeasure, info, line)
    }
}


@OptIn(ExperimentalTextApi::class)
private fun DrawScope.chartRectangleWithText(
    x: Dp, y: Double, textMeasurer: TextMeasurer, infoText: Double, line: LineParameters,
) {
    val rectSize = Size(50.dp.toPx(), 30.dp.toPx())
    val rectTopLeft = Offset(
        x.toPx() - rectSize.width / 1.5.toFloat(),
        y.toFloat() - rectSize.height * 1.5.toFloat()
    )
    val rectBounds = Rect(rectTopLeft, rectSize)
    
    val prefix = line.tooltipLabel.orEmpty()
    val formattedValue = infoText.toFloat().formatToThousandsMillionsBillions()
    val text = if (prefix.isEmpty()) {
        formattedValue
    } else {
        "$prefix:$formattedValue"
    }

    val textStyle = TextStyle(fontSize = 8.sp, color = Color.Black)

    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(text),
        style = textStyle
    )

    val textOffset = Offset(
        rectTopLeft.x + rectSize.width / 2 - textLayoutResult.size.width / 2,
        rectTopLeft.y + rectSize.height / 4 + textLayoutResult.size.height / 2
    )

    drawRoundRect(
        color = line.lineColor,
        topLeft = rectBounds.topLeft,
        size = rectBounds.size,
        cornerRadius = CornerRadius(16.dp.toPx()),
        style = Stroke(width = 1.dp.toPx())
    )

    drawText(
        textMeasurer = textMeasurer,
        text = text,
        style = textStyle,
        topLeft = textOffset
    )

}