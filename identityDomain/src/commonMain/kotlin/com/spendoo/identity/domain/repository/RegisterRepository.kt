package com.spendoo.identity.domain.repository

import com.spendoo.identity.domain.model.AuthenticationTokens
import com.spendoo.identity.domain.model.RegisterRequest

interface RegisterRepository {
    suspend fun requestOTP(email: String)
    suspend fun verifyOTPCode(otpCode: String)
    suspend fun checkUserExistence(username: String): Boolean
    suspend fun register(request: RegisterRequest): AuthenticationTokens
}