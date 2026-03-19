package com.spendoo.identity.presentation.screen.verifyEmail

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText

data class VerifyEmailUiState(
    val actionButtonState: AppButtonState = AppButtonState.Enabled,
    val email: String = "",
    val isForgetPasswordFlow: Boolean = true,
    val otp: String = "",
    val otpError: UiText? = null,
    val timeRemaining: Int = 30,
    val canResend: Boolean = false,
)

