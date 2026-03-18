package com.spendoo.identity.presentation.screen.forgetPassword

interface ForgetPasswordInteractionListener {
    fun onEmailChange(newEmail: String)
    fun onSendCodeClicked()
}

