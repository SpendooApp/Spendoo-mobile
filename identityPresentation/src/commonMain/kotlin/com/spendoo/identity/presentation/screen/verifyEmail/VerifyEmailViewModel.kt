package com.spendoo.identity.presentation.screen.verifyEmail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.api.CreateNewPasswordRoute
import com.spendoo.identity.domain.repository.RegisterRepository
import com.spendoo.identity.domain.repository.ResetPasswordRepository
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.invalid_otp_please_try_again
import spendoo.designsystem.generated.resources.otp_must_be_5_digits
import spendoo.designsystem.generated.resources.unknown_error

class VerifyEmailViewModel(
    private val email: String,
    private val isForgetPasswordFlow: Boolean,
    private val registerRepository: RegisterRepository,
    private val resetPasswordRepository: ResetPasswordRepository,
    private val validationUseCase: ValidationUseCase,
) : BaseViewModel<VerifyEmailUiState>(VerifyEmailUiState()), VerifyEmailInteractionListener {

    private var timerJob: Job? = null

    init {
        updateState {
            copy(
                email = this@VerifyEmailViewModel.email,
                isForgetPasswordFlow = this@VerifyEmailViewModel.isForgetPasswordFlow,
            )
        }
        startTimer()
    }

    override fun onOtpChange(newOtp: String) {
        val onlyDigits = newOtp.filter { it.isDigit() }.take(5)
        updateState { copy(otp = onlyDigits, otpError = null) }
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
                }
            },
            onError = {
                updateState { copy(otpError = UiText.StringRes(Res.string.invalid_otp_please_try_again)) }
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
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = it.message?.let(UiText::DynamicString)
                        ?: UiText.StringRes(Res.string.unknown_error),
                    isSuccess = false,
                )
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
                    UiText.StringRes(Res.string.otp_must_be_5_digits)
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


