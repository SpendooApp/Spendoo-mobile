package com.spendoo.identity.presentation.screen.login

interface LoginInteractionListener {
    fun onLoginClicked()
    fun onSignUpClicked()
    fun onForgotPasswordClicked()
    fun onEmailChange(newEmail: String)
    fun onPasswordChange(newPassword: String)
    fun onTogglePasswordVisibility()
}