package com.spendoo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.appEntryPoint.EntryPoint
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun App(
    isSystemDarkTheme: Boolean = isSystemInDarkTheme()
) {
    SpendooTheme(
        isSystemInDarkTheme = isSystemDarkTheme,
        content = {
            EntryPoint()
        }
    )
}
