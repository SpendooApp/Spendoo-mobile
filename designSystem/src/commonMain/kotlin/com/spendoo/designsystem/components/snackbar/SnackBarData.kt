package com.spendoo.designsystem.components.snackbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class SnackBarData(
    val title: String,
    val message: String? = null,
    val isSuccess: Boolean = true,
    val customLeadingIcon: Painter? = null,
    val duration: Long? = null,
     val iconTint: Color = Color.Unspecified,
    val id: Long = 0L
)