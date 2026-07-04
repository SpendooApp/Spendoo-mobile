package com.spendoo.designsystem.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

class HexagonShape(private val cornerRadius: Dp = 16.dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val width = size.width
            val height = size.height
            val radius = with(density) { cornerRadius.toPx() }

            val p1 = Offset(width / 2f, 0f)
            val p2 = Offset(width, height * 0.25f)
            val p3 = Offset(width, height * 0.75f)
            val p4 = Offset(width / 2f, height)
            val p5 = Offset(0f, height * 0.75f)
            val p6 = Offset(0f, height * 0.25f)

            val points = listOf(p1, p2, p3, p4, p5, p6)

            for (i in points.indices) {
                val current = points[i]
                val prev = points[(i + 5) % 6]
                val next = points[(i + 1) % 6]

                val start = getPointOnLine(current, prev, radius)
                val end = getPointOnLine(current, next, radius)

                if (i == 0) moveTo(start.x, start.y) else lineTo(start.x, start.y)
                quadraticTo(current.x, current.y, end.x, end.y)
            }
            close()
        }
        return Outline.Generic(path)
    }

    private fun getPointOnLine(start: Offset, end: Offset, radius: Float): Offset {
        val dx = end.x - start.x
        val dy = end.y - start.y
        val length = sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        val ratio = if (length == 0f) 0f else radius / length
        return Offset(start.x + dx * ratio, start.y + dy * ratio)
    }
}