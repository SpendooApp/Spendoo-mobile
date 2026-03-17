package com.spendoo.identity.domain.repository

import com.spendoo.identity.domain.model.RegisterRequest

interface RegisterRepository {
    suspend fun reSendOTP(email: String)
    suspend fun verifyOTPCode(email: String, otp: String)
    suspend fun register(request: RegisterRequest)
}