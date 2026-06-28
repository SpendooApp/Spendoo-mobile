package com.spendoo.designsystem.components.pdf

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.pdf.PdfDocument
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import org.koin.core.context.GlobalContext
import java.io.ByteArrayOutputStream

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

private class DummyLifecycleOwner : LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    private val store = ViewModelStore()

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry
    override val viewModelStore: ViewModelStore get() = store
}

actual class PdfLayoutCompiler actual constructor(context: Any?) {
    private val mContext = context
    private val context: Context
        get() = (mContext as? Context) ?: GlobalContext.get().get()

    actual suspend fun compileComposeToPdf(
        widthDp: Int,
        heightDp: Int,
        scale: Float,
        parentContext: androidx.compose.runtime.CompositionContext?,
        content: @Composable () -> Unit
    ): ByteArray {
        val density = context.resources.displayMetrics.density
        val width = (widthDp * density).toInt()
        val height = (heightDp * density).toInt()

        val decorView = context.findActivity()?.window?.decorView as? ViewGroup

        val composeView = ComposeView(context).apply {
            parentContext?.let { setParentCompositionContext(it) }
            setContent {
                Box(modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)) {
                    content()
                }
            }
            visibility = View.INVISIBLE
        }

        val lifecycleOwner = DummyLifecycleOwner()
        composeView.setViewTreeLifecycleOwner(lifecycleOwner)
        composeView.setViewTreeViewModelStoreOwner(lifecycleOwner)
        composeView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)

        // Attach to window to allow active recomposition & rendering passes
        decorView?.addView(composeView, ViewGroup.LayoutParams(width, height))

        try {
            // Wait for composition to complete
            kotlinx.coroutines.yield()
            androidx.compose.runtime.withFrameNanos { }
            androidx.compose.runtime.withFrameNanos { }

            composeView.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
            )
            composeView.layout(0, 0, width, height)

            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            composeView.draw(page.canvas)
            pdfDocument.finishPage(page)

            val outputStream = ByteArrayOutputStream()
            pdfDocument.writeTo(outputStream)
            pdfDocument.close()
            return outputStream.toByteArray()
        } finally {
            // Always detach from window
            decorView?.removeView(composeView)
        }
    }
}
