package com.spendoo.identity.presentation.screen.verifyEmail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.repository.RegisterRepository
import com.spendoo.identity.domain.repository.ResetPasswordRepository
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import com.spendoo.identity.presentation.navigation.CreateNewPasswordRoute
import com.spendoo.identity.presentation.navigation.VerifyEmailRoute
import com.spendoo.identity.presentation.shared.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VerifyEmailViewModel(
    private val registerRepository: RegisterRepository,
    private val resetPasswordRepository: ResetPasswordRepository,
    private val validationUseCase: ValidationUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<VerifyEmailUiState>(VerifyEmailUiState()), VerifyEmailInteractionListener {

    private val navEmail = savedStateHandle.toRoute<VerifyEmailRoute>().email
    private val navIsForgetPasswordFlow =
        savedStateHandle.toRoute<VerifyEmailRoute>().isForgetPasswordFlow
    private var timerJob: Job? = null

    init {
        updateState {
            copy(
                email = navEmail,
                isForgetPasswordFlow = navIsForgetPasswordFlow,
            )
        }
        startTimer()
    }

    override fun onOtpChange(newOtp: String) {
        val onlyDigits = newOtp.filter { it.isDigit() }.take(5)
        updateState { copy(otp = onlyDigits) }
    }

    override fun onVerifyClicked() {
        validateOtp()
        if (state.value.otpError != null) return

        tryToCall(
            onStart = {
                updateState { copy(actionButtonState = AppButtonState.Loading) }
            },
            block = {
                if (state.value.isForgetPasswordFlow) {
                    resetPasswordRepository.verifyOTPCode(
                        email = state.value.email,
                        otp = state.value.otp,
                    )
                } else {
                    registerRepository.verifyOTPCode(
                        email = state.value.email,
                        otp = state.value.otp,
                    )
                }
            },
            onSuccess = {
                if (state.value.isForgetPasswordFlow) {
                    navigate(
                        CreateNewPasswordRoute(
                            email = state.value.email,
                            otp = state.value.otp,
                        )
                    )
                } else {
                    updateBottomNavigationVisibility(true)
                }
            },
            onError = {
                updateState {
                    copy(
                        otpError = UiText.DynamicString("Invalid OTP. Please try again."),
                    )
                }
            },
            onEnd = {
                updateState { copy(actionButtonState = AppButtonState.Enabled) }
            }
        )
    }

    override fun onResendClicked() {
        tryToCall(
            block = {
                if (state.value.isForgetPasswordFlow) {
                    resetPasswordRepository.reSendOtp(state.value.email)
                } else {
                    registerRepository.reSendOTP(state.value.email)
                }
            },
            onSuccess = {
                updateState {
                    it.copy(
                        timeRemaining = 50,
                        canResend = false,
                        otpError = null,
                    )
                }
                startTimer()
            },
            onError = {
                println("Resend OTP error: ${it.message}")
            }
        )
    }

    private fun validateOtp() {
        val isValid = validationUseCase.validateOtp(state.value.otp)
        updateState {
            copy(
                otpError = if (isValid) {
                    null
                } else {
                    UiText.DynamicString("OTP must be 5 digits")
                }
            )
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (state.value.timeRemaining > 0) {
                delay(1000)
                updateState {
                    it.copy(
                        timeRemaining = it.timeRemaining - 1,
                        canResend = it.timeRemaining - 1 <= 0
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}


