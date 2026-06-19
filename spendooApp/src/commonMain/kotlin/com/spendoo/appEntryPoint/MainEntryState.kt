package com.spendoo.appEntryPoint

import com.spendoo.designsystem.components.snackbar.SnackBarData

data class MainEntryState(
    val isSnackBarVisible: Boolean = false,
    val snackBarData: SnackBarData = SnackBarData(""),
    val isAddTransactionBottomSheetVisible: Boolean = false,
)