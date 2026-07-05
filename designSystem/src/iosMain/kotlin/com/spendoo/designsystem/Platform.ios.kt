package com.spendoo.designsystem

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry

actual fun platform() = "iOS"

@OptIn(ExperimentalComposeUiApi::class)
actual fun createClipEntry(text: String, label: String): ClipEntry {
    return ClipEntry.withPlainText(text)
}