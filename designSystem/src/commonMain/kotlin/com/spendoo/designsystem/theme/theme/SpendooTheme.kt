package com.spendoo.designsystem.theme.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.spendoo.designsystem.theme.color.scheme.ColorScheme
import com.spendoo.designsystem.theme.color.scheme.DarkColorScheme
import com.spendoo.designsystem.theme.color.scheme.LightColorScheme
import com.spendoo.designsystem.theme.color.scheme.LocalColorScheme
import com.spendoo.designsystem.theme.typography.LocalTypography
import com.spendoo.designsystem.theme.typography.Typography
import com.spendoo.designsystem.theme.typography.createThemeTypography
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi

@OptIn(InternalResourceApi::class, ExperimentalResourceApi::class)
@Composable
fun SpendooTheme(
    isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isSystemInDarkTheme) DarkColorScheme else LightColorScheme
    val typography = createThemeTypography()

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalTypography provides typography,
    ) {
        content()
    }
}

object Theme {
    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = LocalColorScheme.current

    val typography: Typography
        @Composable @ReadOnlyComposable get() = LocalTypography.current
}