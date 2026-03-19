package com.spendoo.identity.presentation.screen.createNewPassword

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.repository.ResetPasswordRepository
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import com.spendoo.identity.presentation.navigation.CreateNewPasswordRoute
import com.spendoo.identity.presentation.navigation.LoginRoute
import com.spendoo.identity.presentation.shared.BaseViewModel

class CreateNewPasswordViewModel(
    private val resetPasswordRepository: ResetPasswordRepository,
    private val validationUseCase: ValidationUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CreateNewPasswordUiState>(CreateNewPasswordUiState()),
    CreateNewPasswordInteractionListener {

    private val navEmail = savedStateHandle.toRoute<CreateNewPasswordRoute>().email
    private val navOtp = savedStateHandle.toRoute<CreateNewPasswordRoute>().otp

    init {
        updateState {
            copy(
                email = navEmail,
                otp = navOtp
            )
        }
    }

    override fun onPasswordChange(newPassword: String) {
        updateState { copy(password = newPassword) }
        validatePassword()
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
                println("Reset password error: ${it.message}")
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
                    UiText.DynamicString("Invalid password")
                }
            )
        }
    }
}

