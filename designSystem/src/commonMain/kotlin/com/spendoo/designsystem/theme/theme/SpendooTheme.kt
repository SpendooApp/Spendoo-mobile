package com.spendoo.designsystem.theme.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.spendoo.designsystem.theme.color.scheme.ColorScheme
import com.spendoo.designsystem.theme.color.scheme.DarkColorScheme
import com.spendoo.designsystem.theme.color.scheme.LightColorScheme
import com.spendoo.designsystem.theme.color.scheme.LocalColorScheme
import com.spendoo.designsystem.theme.typography.LocalTypography
import com.spendoo.designsystem.theme.typography.Typography
import com.spendoo.designsystem.theme.typography.createThemeTypography
import com.spendoo.designsystem.utils.AppLanguage

internal val LocalIsDarkTheme = staticCompositionLocalOf<Boolean?> { null }

@Composable
fun SpendooTheme(
    language: String = AppLanguage.English.iso,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = createThemeTypography()

    val layoutDirection = remember(language) {
        if (language == AppLanguage.English.iso) LayoutDirection.Ltr else LayoutDirection.Rtl
    }

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalTypography provides typography,
        LocalIsDarkTheme provides darkTheme,
        LocalLayoutDirection provides layoutDirection
    ) {
        content()
    }
}

object Theme {
    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = LocalColorScheme.current

    val typography: Typography
        @Composable @ReadOnlyComposable get() = LocalTypography.current

    val isDarkTheme: Boolean
        @Composable @ReadOnlyComposable get() = LocalIsDarkTheme.current ?: isSystemInDarkTheme()
}