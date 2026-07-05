package com.spendoo.identity.presentation.screen.createNewPassword

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.api.LoginRoute
import com.spendoo.identity.domain.repository.ResetPasswordRepository
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.invalid_password
import spendoo.designsystem.generated.resources.unknown_error

class CreateNewPasswordViewModel(
    private val email: String,
    private val otp: String,
    private val resetPasswordRepository: ResetPasswordRepository,
    private val validationUseCase: ValidationUseCase,
) : BaseViewModel<CreateNewPasswordUiState>(CreateNewPasswordUiState()),
    CreateNewPasswordInteractionListener {

    init {
        updateState {
            copy(
                email = this@CreateNewPasswordViewModel.email,
                otp = this@CreateNewPasswordViewModel.otp
            )
        }
    }

    override fun onPasswordChange(newPassword: String) {
        updateState { copy(password = newPassword, passwordError = null) }
    }

    override fun onTogglePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    override fun onResetPasswordClicked() {
        validatePassword()
        if (state.value.passwordError != null) return

        tryToCall(
            onStart = {
                updateState { copy(actionButtonState = AppButtonState.Loading) }
            },
            block = {
                resetPasswordRepository.resetPassword(
                    email = state.value.email,
                    otp = state.value.otp,
                    newPassword = state.value.password,
                )
            },
            onSuccess = {
                navigate(LoginRoute)
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

    private fun validatePassword() {
        val isValid = validationUseCase.validatePassword(state.value.password)
        updateState {
            copy(
                passwordError = if (isValid) {
                    null
                } else {
                    UiText.StringRes(Res.string.invalid_password)
                }
            )
        }
    }
}

