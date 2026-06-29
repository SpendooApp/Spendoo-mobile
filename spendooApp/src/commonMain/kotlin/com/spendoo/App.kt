package com.spendoo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.appEntryPoint.EntryPoint
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.util.AppTheme
import com.spendoo.util.AppLocalizer
import com.spendoo.util.SetSystemBarsAppearance
import org.koin.compose.koinInject

@Preview
@Composable
fun App(
    isSystemDarkTheme: Boolean = isSystemInDarkTheme(),
    settingsRepository: SettingsRepository = koinInject(),
    appLocalizer: AppLocalizer = koinInject()
) {
    val currentTheme by settingsRepository.observeAppTheme().collectAsStateWithLifecycle()
    val currentLanguage by settingsRepository.observeAppLanguage().collectAsStateWithLifecycle()

    val isDarkTheme = when (currentTheme) {
        AppTheme.SYSTEM -> isSystemDarkTheme
        AppTheme.DARK -> true
        AppTheme.LIGHT -> false
    }

    SpendooTheme(
        darkTheme = isDarkTheme,
        content = {
            SetSystemBarsAppearance(currentTheme)
            EntryPoint()
        }
    )
}
