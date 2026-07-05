package com.spendoo.designsystem.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class RibbonShape(private val indentRatio: Float = 0.15f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val width = size.width
            val height = size.height
            val indent = width * indentRatio

            moveTo(0f, 0f)
            lineTo(width, 0f)
            lineTo(width - indent, height / 2f)
            lineTo(width, height)
            lineTo(0f, height)
            lineTo(indent, height / 2f)
            close()
        }
        return Outline.Generic(path)
    }
}