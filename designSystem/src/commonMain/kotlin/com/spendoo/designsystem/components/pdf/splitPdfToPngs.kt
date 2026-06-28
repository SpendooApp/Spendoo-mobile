package com.spendoo.designsystem.components.pdf

expect suspend fun splitPdfToPngs(pdfData: ByteArray): List<ByteArray>
