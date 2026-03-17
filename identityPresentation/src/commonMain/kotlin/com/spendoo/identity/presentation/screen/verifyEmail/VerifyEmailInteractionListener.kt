package com.spendoo.identity.presentation.screen.verifyEmail

interface VerifyEmailInteractionListener {
    fun onOtpChange(newOtp: String)
    fun onVerifyClicked()
    fun onResendClicked()
}

