package com.spendoo.appEntryPoint

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.spendoo.designsystem.components.snackbar.SnackBarData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainEntryViewModel : ViewModel(), MainEntryInteractionListener {
    private val _state = MutableStateFlow(MainEntryState())
    val state = _state.asStateFlow()

    private var snackBarId = 0L

    override fun onBottomNavigationChanged(isShowed: Boolean) {
        _state.update { it.copy(showBottomNavigation = isShowed) }
    }

    override fun setActiveFeature(feature: Feature) {
        _state.update { it.copy(activeFeature = feature) }
    }

    override fun onAddTransactionRequested() {
        _state.update { it.copy(isAddTransactionBottomSheetVisible = true) }
    }

    override fun onAddTransactionDismissed() {
        _state.update { it.copy(isAddTransactionBottomSheetVisible = false) }
    }

    override fun onTransactionAdded() {
        _state.update {
            it.copy(
                isAddTransactionBottomSheetVisible = false,
                reloadRequestId = it.reloadRequestId + 1,
                reloadTarget = it.activeFeature
            )
        }
    }

    override fun showSnackBar(
        title: String,
        message: String?,
        isSuccess: Boolean,
        customLeadingIcon: Painter?,
        duration: Long?,
        iconTint: Color
    ) {
        _state.update {
            it.copy(
                isSnackBarVisible = true,
                snackBarData = SnackBarData(
                    title = title,
                    message = message,
                    isSuccess = isSuccess,
                    customLeadingIcon = customLeadingIcon,
                    duration = duration,
                    iconTint = iconTint,
                    id = ++snackBarId
                )
            )
        }
    }

    override fun hideSnackBar() {
        println("hideSnackBar called")
        _state.update { it.copy(isSnackBarVisible = false) }
    }
}
