package com.spendoo.appEntryPoint

import com.spendoo.designsystem.components.snackbar.SnackBarData

data class MainEntryState(
    val activeFeature: Feature = Feature.Home,
    val showBottomNavigation: Boolean = true,
    val isSnackBarVisible: Boolean = false,
    val snackBarData: SnackBarData = SnackBarData(""),
    val isAddTransactionBottomSheetVisible: Boolean = false,
    val reloadRequestId: Long = 0L,
    val reloadTarget: Feature? = null,
)

enum class Feature {
    Home, Categories, Stats, ChatBot, Profile, Payments
}