package com.spendoo.identity.presentation.screen.forgetPassword

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText

data class ForgetPasswordUiState(
    val actionButtonState: AppButtonState = AppButtonState.Enabled,
    val email: String = "",
    val emailError: UiText? = null,
)

