package com.spendoo.identity.presentation.screen.createNewPassword

interface CreateNewPasswordInteractionListener {
    fun onPasswordChange(newPassword: String)
    fun onTogglePasswordVisibility()
    fun onResetPasswordClicked()
}

