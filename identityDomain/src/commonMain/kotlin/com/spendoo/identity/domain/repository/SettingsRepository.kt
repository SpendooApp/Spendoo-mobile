package com.spendoo.identity.domain.repository

import com.spendoo.identity.domain.util.AppLanguage
import com.spendoo.identity.domain.util.AppTheme
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    suspend fun applyLanguage(appLanguage: AppLanguage)
    fun observeAppLanguage(): StateFlow<AppLanguage>
    fun getCurrentAppLanguage(): AppLanguage
    suspend fun applyAppTheme(appTheme: AppTheme)
    fun observeAppTheme(): StateFlow<AppTheme>
    fun getCurrentTheme(): AppTheme
    fun isOnboardingComplete(): Boolean
    fun observeOnBoardingCompleted(): StateFlow<Boolean>
    fun setOnboardingCompleted(value: Boolean)
}