package com.spendoo.identity.domain.repository

interface ResetPasswordRepository {
    suspend fun requestOTP(email: String)
    suspend fun verifyOTPCode(otpCode: String)
    suspend fun resetPassword(newPassword: String, confirmPassword: String)
}