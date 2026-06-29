package com.spendoo.designsystem.components.pdf

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext

data class PdfPageInput(
    val widthDp: Int,
    val heightDp: Int,
    val content: @Composable () -> Unit
)

expect class PdfLayoutCompiler(context: Any? = null) {
    suspend fun compileComposeToPdf(
        widthDp: Int,
        heightDp: Int,
        scale: Float,
        parentContext: CompositionContext? = null,
        content: @Composable () -> Unit
    ): ByteArray

    suspend fun compileMultiplePagesToPdf(
        pages: List<PdfPageInput>,
        scale: Float,
        parentContext: CompositionContext? = null
    ): ByteArray
}
