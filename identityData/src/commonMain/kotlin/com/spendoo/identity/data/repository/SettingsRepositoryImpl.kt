package com.spendoo.identity.data.repository

import com.russhwolf.settings.Settings
import com.spendoo.identity.data.dataSource.local.setting.appLanguage
import com.spendoo.identity.data.dataSource.local.setting.appTheme
import com.spendoo.identity.data.dataSource.local.setting.onBoardingCompleted
import com.spendoo.identity.data.dataSource.local.setting.reminderEnabled
import com.spendoo.identity.data.dataSource.local.setting.homeOffersEnabled
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.util.AppLanguage
import com.spendoo.identity.domain.util.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepositoryImpl(
    private val settings: Settings,
) : SettingsRepository {
    private val _appLanguageFlow = MutableStateFlow(settings.appLanguage.toAppLanguage())
    private val _appThemeFlow = MutableStateFlow(settings.appTheme.toAppTheme())
    private val _onBoardingFlow = MutableStateFlow(settings.onBoardingCompleted)
    private val _reminderEnabledFlow = MutableStateFlow(settings.reminderEnabled)
    private val _homeOffersEnabledFlow = MutableStateFlow(settings.homeOffersEnabled)


    override suspend fun applyLanguage(appLanguage: AppLanguage) {
        settings.appLanguage = appLanguage.iso
        _appLanguageFlow.value = appLanguage
    }

    override fun observeAppLanguage(): StateFlow<AppLanguage> = _appLanguageFlow

    override fun getCurrentAppLanguage(): AppLanguage = settings.appLanguage.toAppLanguage()
    override fun getCurrentTheme(): AppTheme = settings.appTheme.toAppTheme()

    override suspend fun applyAppTheme(appTheme: AppTheme) {
        settings.appTheme = appTheme.name
        _appThemeFlow.value = appTheme
    }

    override fun observeAppTheme(): StateFlow<AppTheme> = _appThemeFlow

    private fun String.toAppLanguage(): AppLanguage {
        return when (this) {
            AppLanguage.ENGLISH.iso -> AppLanguage.ENGLISH
            AppLanguage.ARABIC.iso -> AppLanguage.ARABIC
            else -> AppLanguage.DEFAULT
        }
    }

    private fun String.toAppTheme(): AppTheme {
        return when (this) {
            AppTheme.DARK.name -> AppTheme.DARK
            AppTheme.LIGHT.name -> AppTheme.LIGHT
            else -> AppTheme.SYSTEM
        }
    }

    override fun isOnboardingComplete() = settings.onBoardingCompleted

    override fun observeOnBoardingCompleted(): StateFlow<Boolean> = _onBoardingFlow

    override fun setOnboardingCompleted(value: Boolean) {
        settings.onBoardingCompleted = value
        _onBoardingFlow.value = value
    }

    override fun isReminderEnabled(): Boolean = settings.reminderEnabled

    override fun observeReminderEnabled(): StateFlow<Boolean> = _reminderEnabledFlow

    override suspend fun setReminderEnabled(value: Boolean) {
        settings.reminderEnabled = value
        _reminderEnabledFlow.value = value
    }

    override fun isHomeOffersEnabled(): Boolean = settings.homeOffersEnabled

    override fun observeHomeOffersEnabled(): StateFlow<Boolean> = _homeOffersEnabledFlow

    override suspend fun setHomeOffersEnabled(value: Boolean) {
        settings.homeOffersEnabled = value
        _homeOffersEnabledFlow.value = value
    }
}