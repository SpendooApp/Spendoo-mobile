package com.spendoo.designsystem

import androidx.compose.ui.platform.ClipEntry

expect fun platform(): String

expect fun createClipEntry(text: String, label: String): ClipEntry