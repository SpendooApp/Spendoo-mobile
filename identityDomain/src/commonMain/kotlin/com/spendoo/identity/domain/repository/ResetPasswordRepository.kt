package com.spendoo.identity.domain.repository

interface ResetPasswordRepository {
    suspend fun requestOTP(email: String)
    suspend fun verifyOTPCode(email: String, otp: String)
    suspend fun resetPassword(email: String, otp: String, newPassword: String)
    suspend fun reSendOtp(email: String)
}