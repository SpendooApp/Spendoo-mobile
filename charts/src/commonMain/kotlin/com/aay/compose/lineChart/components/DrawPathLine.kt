package com.aay.compose.lineChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import com.aay.compose.lineChart.model.LineParameters


internal fun DrawScope.drawPathLineWrapper(
    lineParameter: LineParameters,
    fillPath: Path,
    solidPath: Path,
    dashedPath: Path,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    function: (LineParameters, Int) -> Unit,
) {
    lineParameter.data.indices.forEach { index ->
        function(lineParameter, index)
    }
    clipRect(right = size.width * animatedProgress.value) {
        drawPath(
            path = solidPath, color = lineParameter.lineColor, style = Stroke(
                width = 3.dp.toPx(), cap = StrokeCap.Round
            )
        )
        drawPath(
            path = dashedPath, color = lineParameter.lineColor, style = Stroke(
                width = 3.dp.toPx(), cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
            )
        )
    }
}