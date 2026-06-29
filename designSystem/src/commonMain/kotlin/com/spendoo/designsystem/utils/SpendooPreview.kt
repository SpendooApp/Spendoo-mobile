package com.spendoo.designsystem.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun SpendooPreview(
    modifier: Modifier = Modifier,
    color: Color = Theme.colorScheme.background.primary,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    SpendooTheme(darkTheme = darkTheme) {
        Surface(modifier = modifier, color = color) {
            content()
        }
    }
}