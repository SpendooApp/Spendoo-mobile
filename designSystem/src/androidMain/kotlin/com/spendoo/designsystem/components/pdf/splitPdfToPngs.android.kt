package com.spendoo.designsystem.components.pdf

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import androidx.core.graphics.createBitmap

private const val IMAGE_SCALE = 1.67f // Scale up for better image rendering quality

actual suspend fun splitPdfToPngs(pdfData: ByteArray): List<PdfPage> = withContext(Dispatchers.IO) {
    runCatching {
        val tempFile = File.createTempFile("temp_pdf", ".pdf")
        tempFile.writeBytes(pdfData)
        renderPdfToPngs(tempFile)
    }.getOrElse { emptyList() }
}

private fun renderPdfToPngs(pdfFile: File): List<PdfPage> {
    val fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
    val renderer = PdfRenderer(fileDescriptor)

    return buildList {
        repeat(renderer.pageCount) { index ->
            val page = renderer.renderPageToPng(index)
            add(page)
        }
    }.also {
        renderer.close()
        fileDescriptor.close()
        pdfFile.delete() // Clean up temp file
    }
}

private fun PdfRenderer.renderPageToPng(pageIndex: Int): PdfPage {
    openPage(pageIndex).use { page ->
        val width = (page.width * IMAGE_SCALE).toInt()
        val height = (page.height * IMAGE_SCALE).toInt()
        val bitmap = createBitmap(width, height)

        val matrix = Matrix().apply {
            postScale(IMAGE_SCALE, IMAGE_SCALE)
        }

        page.render(bitmap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        val pngData = ByteArrayOutputStream().use { output ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            bitmap.recycle()
            output.toByteArray()
        }
        return PdfPage(pngData = pngData, width = width, height = height)
    }
}
