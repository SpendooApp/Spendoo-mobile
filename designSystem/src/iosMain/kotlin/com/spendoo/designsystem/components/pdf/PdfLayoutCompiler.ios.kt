package com.spendoo.designsystem.components.pdf

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.CompositionContext
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import platform.UIKit.UIGraphicsPDFRenderer
import platform.UIKit.UIGraphicsPDFRendererFormat
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.CoreGraphics.CGRectMake
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.posix.memcpy

import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGSizeMake

actual class PdfLayoutCompiler actual constructor(context: Any?) {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun compileComposeToPdf(
        widthDp: Int,
        heightDp: Int,
        scale: Float,
        parentContext: CompositionContext?,
        content: @Composable () -> Unit
    ): ByteArray {
        val controller = androidx.compose.ui.window.ComposeUIViewController {
            SpendooTheme(darkTheme = Theme.isDarkTheme) {
                Box(modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)) {
                    content()
                }
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

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun compileMultiplePagesToPdf(
        pages: List<PdfPageInput>,
        scale: Float,
        parentContext: CompositionContext?
    ): ByteArray {
        val format = UIGraphicsPDFRendererFormat()
        val defaultWidth = pages.firstOrNull()?.widthDp?.toDouble() ?: 595.0
        val defaultHeight = pages.firstOrNull()?.heightDp?.toDouble() ?: 842.0
        val renderer = UIGraphicsPDFRenderer(bounds = CGRectMake(0.0, 0.0, defaultWidth, defaultHeight), format = format)
        
        val data = renderer.PDFDataWithActions { context ->
            if (context != null) {
                pages.forEach { pageInput ->
                    val width = pageInput.widthDp.toDouble()
                    
                    val controller = androidx.compose.ui.window.ComposeUIViewController {
                        SpendooTheme(darkTheme = Theme.isDarkTheme) {
                            Box(modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)) {
                                pageInput.content()
                            }
                        }
                    }
                    val view = controller.view
                    
                    val height = if (pageInput.heightDp > 0) {
                        pageInput.heightDp.toDouble()
                    } else {
                        view.setFrame(CGRectMake(0.0, 0.0, width, 10000.0))
                        view.setNeedsLayout()
                        view.layoutIfNeeded()
                        val fittedSize = view.sizeThatFits(CGSizeMake(width, Double.MAX_VALUE))
                        fittedSize.useContents { height }
                    }
                    
                    view.setFrame(CGRectMake(0.0, 0.0, width, height))
                    view.setNeedsLayout()
                    view.layoutIfNeeded()

                    context.beginPageWithBounds(CGRectMake(0.0, 0.0, width, height), emptyMap<Any?, Any?>())
                    view.layer.renderInContext(UIGraphicsGetCurrentContext())
                }
            }
        }

        val bytes = ByteArray(data.length.toInt())
        memcpy(bytes.refTo(0), data.bytes, data.length)
        return bytes
    }
}
