package com.spendoo.appEntryPoint

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import com.spendoo.categories.api.AddTransactionRoute
import com.spendoo.designsystem.components.snackbar.SnackBarData
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.asStringSuspend
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainEntryViewModel : BaseViewModel<MainEntryState>(MainEntryState()),
    MainEntryInteractionListener {

    private var snackBarId = 0L

    init {
        viewModelScope.launch {
            snackBarManager.snackBarEvent.collectLatest { event ->
                val resolvedTitle = event.title.asStringSuspend()
                val resolvedMessage = event.message?.asStringSuspend()
                updateState {
                    it.copy(
                        isSnackBarVisible = true,
                        snackBarData = SnackBarData(
                            title = resolvedTitle,
                            message = resolvedMessage,
                            isSuccess = event.isSuccess,
                            customLeadingIcon = event.customLeadingIcon,
                            duration = event.duration,
                            iconTint = event.iconTint,
                            id = ++snackBarId
                        )
                    )
                }
            }
        }
    }

    override fun onAddTransactionRequested() {
        updateState { it.copy(isAddTransactionBottomSheetVisible = true) }
    }

    override fun onAddTransactionClicked() {
        navigate(AddTransactionRoute)
    }

    override fun onAddTransactionDismissed() {
        updateState { it.copy(isAddTransactionBottomSheetVisible = false) }
    }

    override fun onTransactionAdded() {
        updateState {
            it.copy(
                isAddTransactionBottomSheetVisible = false,
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
        updateState {
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
        updateState { it.copy(isSnackBarVisible = false) }
    }

    override fun resetToRoute(route: NavKey, forceNavigate: Boolean) {
        resetTo(route, forceNavigate)
    }

    override fun navigateToRoute(route: NavKey, forceNavigate: Boolean) {
        navigate(route, forceNavigate)
    }
}