package com.spendoo.identity.presentation.screen.login

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText

data class LoginScreenState(
    val actionButtonState: AppButtonState = AppButtonState.Enabled,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isPasswordVisible: Boolean = true,
)