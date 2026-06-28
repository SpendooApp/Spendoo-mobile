package com.spendoo.designsystem.components.pdf

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.Box
import platform.UIKit.UIGraphicsPDFRenderer
import platform.UIKit.UIGraphicsPDFRendererFormat
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.CoreGraphics.CGRectMake
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.posix.memcpy

actual class PdfLayoutCompiler actual constructor(context: Any?) {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun compileComposeToPdf(
        widthDp: Int,
        heightDp: Int,
        scale: Float,
        parentContext: androidx.compose.runtime.CompositionContext?,
        content: @Composable () -> Unit
    ): ByteArray {
        val controller = androidx.compose.ui.window.ComposeUIViewController {
            Box(modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)) {
                content()
            }
        }
        val view = controller.view
        val width = widthDp.toDouble()
        val height = heightDp.toDouble()
        view.setFrame(CGRectMake(0.0, 0.0, width, height))
        view.setNeedsLayout()
        view.layoutIfNeeded()

        val format = UIGraphicsPDFRendererFormat()
        val renderer = UIGraphicsPDFRenderer(bounds = CGRectMake(0.0, 0.0, width, height), format = format)
        val data = renderer.PDFDataWithActions { context ->
            context?.beginPage()
            view.layer.renderInContext(UIGraphicsGetCurrentContext())
        }

        val bytes = ByteArray(data.length.toInt())
        memcpy(bytes.refTo(0), data.bytes, data.length)
        return bytes
    }
}
