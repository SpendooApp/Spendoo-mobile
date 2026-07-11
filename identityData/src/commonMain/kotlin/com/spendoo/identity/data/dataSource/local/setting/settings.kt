package com.spendoo.identity.data.dataSource.local.setting

import com.russhwolf.settings.Settings
import com.spendoo.identity.domain.util.AppTheme

internal var Settings.accessToken: String
    get() = getString(ACCESS_TOKEN, "")
    set(value) = putString(ACCESS_TOKEN, value)

internal var Settings.refreshToken: String
    get() = getString(REFRESH_TOKEN, "")
    set(value) = putString(REFRESH_TOKEN, value)

internal var Settings.appLanguage: String
    get() = getString(APP_LANGUAGE, "")
    set(value) = putString(APP_LANGUAGE, value)

internal var Settings.appTheme: String
    get() = getString(APP_THEME, AppTheme.SYSTEM.name)
    set(value) = putString(APP_THEME, value)

internal var Settings.onBoardingCompleted: Boolean
    get() = getBoolean(OnBOARDING_COMPLETED, false)
    set(value) = putBoolean(OnBOARDING_COMPLETED, value)

internal var Settings.reminderEnabled: Boolean
    get() = getBoolean(REMINDER_ENABLED, true)
    set(value) = putBoolean(REMINDER_ENABLED, value)

internal var Settings.homeOffersEnabled: Boolean
    get() = getBoolean(HOME_OFFERS_ENABLED, true)
    set(value) = putBoolean(HOME_OFFERS_ENABLED, value)

const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"
const val APP_LANGUAGE = "app_language"
const val APP_THEME = "app_theme"
const val OnBOARDING_COMPLETED = "onboarding_completed"
const val REMINDER_ENABLED = "reminder_enabled"
const val HOME_OFFERS_ENABLED = "home_offers_enabled"
