package com.spendoo.appEntryPoint

import com.spendoo.designsystem.components.snackbar.SnackBarDate

data class MainEntryState(
    val activeFeature: Feature = Feature.Home,
    val showBottomNavigation: Boolean = true,
    val isSnackBarVisible: Boolean = false,
    val snackBarDate: SnackBarDate = SnackBarDate(""),
)

enum class Feature {
    Home, Categories, Stats, ChatBot, Profile, Payments
}