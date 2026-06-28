package com.spendoo.designsystem.components.pdf

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext

expect class PdfLayoutCompiler(context: Any? = null) {
    suspend fun compileComposeToPdf(
        widthDp: Int,
        heightDp: Int,
        scale: Float,
        parentContext: CompositionContext? = null,
        content: @Composable () -> Unit
    ): ByteArray
}
