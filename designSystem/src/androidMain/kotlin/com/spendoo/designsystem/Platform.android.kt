package com.spendoo.designsystem

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry

actual fun platform() = "Android"

actual fun createClipEntry(text: String, label: String): ClipEntry {
    val clipData = ClipData.newPlainText(label, text)
    return ClipEntry(clipData = clipData)
}