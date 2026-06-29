package com.spendoo.identity.presentation.screen.forgetPassword

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.api.VerifyEmailRoute
import com.spendoo.identity.domain.repository.ResetPasswordRepository
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.invalid_email
import spendoo.designsystem.generated.resources.unknown_error

class ForgetPasswordViewModel(
    private val resetPasswordRepository: ResetPasswordRepository,
    private val validationUseCase: ValidationUseCase,
) : BaseViewModel<ForgetPasswordUiState>(ForgetPasswordUiState()),
    ForgetPasswordInteractionListener {

    override fun onEmailChange(newEmail: String) {
        updateState { copy(email = newEmail, emailError = null) }
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
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = it.message?.let(UiText::DynamicString)
                        ?: UiText.StringRes(Res.string.unknown_error),
                    isSuccess = false,
                )
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
                    UiText.StringRes(Res.string.invalid_email)
                }
            )
        }
    }
}

