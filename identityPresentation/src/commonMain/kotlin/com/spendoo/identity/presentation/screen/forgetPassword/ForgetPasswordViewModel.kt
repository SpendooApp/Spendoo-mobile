package com.spendoo.identity.presentation.screen.forgetPassword

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.repository.ResetPasswordRepository
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import com.spendoo.identity.presentation.navigation.VerifyEmailRoute
import com.spendoo.identity.presentation.shared.BaseViewModel

class ForgetPasswordViewModel(
    private val resetPasswordRepository: ResetPasswordRepository,
    private val validationUseCase: ValidationUseCase,
) : BaseViewModel<ForgetPasswordUiState>(ForgetPasswordUiState()),
    ForgetPasswordInteractionListener {

    override fun onEmailChange(newEmail: String) {
        updateState { copy(email = newEmail) }
        validateEmail()
    }

    override fun onSendCodeClicked() {
        validateEmail()
        if (state.value.emailError != null) return

        tryToCall(
            onStart = {
                updateState { copy(actionButtonState = AppButtonState.Loading) }
            },
            block = {
                resetPasswordRepository.requestOTP(state.value.email)
            },
            onSuccess = {
                navigate(VerifyEmailRoute(email = state.value.email, isForgetPasswordFlow = true))
            },
            onError = {
                println("Request OTP error: ${it.message}")
            },
            onEnd = {
                updateState { copy(actionButtonState = AppButtonState.Enabled) }
            }
        )
    }

    private fun validateEmail() {
        val isValid = validationUseCase.validateEmail(state.value.email)
        updateState {
            copy(
                emailError = if (isValid) {
                    null
                } else {
                    UiText.DynamicString("Invalid email")
                }
            )
        }
    }
}

