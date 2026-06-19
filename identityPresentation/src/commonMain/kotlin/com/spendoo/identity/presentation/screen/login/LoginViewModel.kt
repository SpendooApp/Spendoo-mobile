package com.spendoo.identity.presentation.screen.login

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.api.ForgetPasswordRoute
import com.spendoo.identity.api.SignUpRoute
import com.spendoo.identity.domain.useCase.LoginUseCase
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.invalid_email
import spendoo.designsystem.generated.resources.invalid_password
import spendoo.designsystem.generated.resources.unknown_error

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val validationUseCase: ValidationUseCase
) : BaseViewModel<LoginScreenState>(LoginScreenState()), LoginInteractionListener {

    override fun onLoginClicked() {
        validateFields()
        if (state.value.emailError != null || state.value.passwordError != null) return

        tryToCall(
            onStart = {
                updateState { copy(actionButtonState = AppButtonState.Loading) }
            },
            block = {
                loginUseCase.login(
                    email = state.value.email,
                    password = state.value.password
                )
            },
            onSuccess = { },
            onError = { error ->
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = error.message?.let(UiText::DynamicString)
                        ?: UiText.StringRes(Res.string.unknown_error),
                    isSuccess = false,
                )
            },
            onEnd = {
                updateState { copy(actionButtonState = AppButtonState.Enabled) }
            }
        )
    }

    private fun validateFields() {
        validateEmail()
        validatePassword()
        if (state.value.emailError == null && state.value.passwordError == null) {
            updateState { copy(actionButtonState = AppButtonState.Enabled) }
        }
    }

    private fun validateEmail() {
        when (validationUseCase.validateEmail(state.value.email)) {
            true -> updateState { copy(emailError = null) }
            else -> updateState { copy(emailError = UiText.StringRes(Res.string.invalid_email)) }
        }
    }

    private fun validatePassword() {
        when (validationUseCase.validatePassword(state.value.password)) {
            true -> updateState { copy(passwordError = null) }
            else -> updateState { copy(passwordError = UiText.StringRes(Res.string.invalid_password)) }
        }
    }

    override fun onSignUpClicked() {
        navigate(SignUpRoute)
    }

    override fun onForgotPasswordClicked() {
        navigate(ForgetPasswordRoute)
    }

    override fun onEmailChange(newEmail: String) {
        updateState { copy(email = newEmail) }
        validateEmail()
    }

    override fun onPasswordChange(newPassword: String) {
        updateState { copy(password = newPassword) }
        validatePassword()
    }

    override fun onTogglePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }
}