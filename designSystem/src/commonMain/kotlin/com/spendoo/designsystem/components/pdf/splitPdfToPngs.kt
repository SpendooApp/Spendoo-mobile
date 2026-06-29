package com.spendoo.designsystem.components.pdf

data class PdfPage(
    val pngData: ByteArray,
    val width: Int,
    val height: Int
) {
    val aspectRatio: Float get() = if (height > 0) width.toFloat() / height.toFloat() else 1f
}

expect suspend fun splitPdfToPngs(pdfData: ByteArray): List<PdfPage>
