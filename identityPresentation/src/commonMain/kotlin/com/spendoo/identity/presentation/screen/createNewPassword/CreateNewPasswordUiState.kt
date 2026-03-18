package com.spendoo.identity.presentation.screen.createNewPassword

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText

data class CreateNewPasswordUiState(
    val actionButtonState: AppButtonState = AppButtonState.Enabled,
    val email: String = "",
    val otp: String = "",
    val password: String = "",
    val passwordError: UiText? = null,
    val isPasswordVisible: Boolean = true,
)

