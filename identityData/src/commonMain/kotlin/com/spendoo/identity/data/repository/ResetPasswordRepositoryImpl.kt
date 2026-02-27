package com.spendoo.identity.data.repository

import com.spendoo.identity.domain.repository.ResetPasswordRepository
import io.ktor.client.HttpClient
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.OtpRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.response.OtpResponse
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.ResetPasswordRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.VerifyOtpRequestDto
import com.spendoo.identity.data.utils.postJson
import com.spendoo.identity.data.utils.safeWrapper

class ResetPasswordRepositoryImpl(
    private val client: HttpClient
) : ResetPasswordRepository {
    private var sessionId = ""

    override suspend fun requestOTP(email: String) {
        safeWrapper {
            val response: OtpResponse =
                client.postJson(
                    OtpRequestDto(email), RESET_PASSWORD_REQUEST_OTP
                )
            sessionId = response.sessionId
        }
    }

    override suspend fun verifyOTPCode(otpCode: String) {
        safeWrapper<String> {
            client.postJson(
                VerifyOtpRequestDto(
                    otpCode,
                    sessionId
                ), RESET_PASSWORD_VERIFY_OTP
            )
        }
    }

    override suspend fun resetPassword(newPassword: String, confirmPassword: String) {
        safeWrapper<String> {
            client.postJson(
                ResetPasswordRequestDto(newPassword, confirmPassword, sessionId),
                RESET_PASSWORD
            )
        }
    }

    companion object {
        const val RESET_PASSWORD_REQUEST_OTP = "identity/authentication/request-reset-password-otp"
        const val RESET_PASSWORD_VERIFY_OTP = "identity/authentication/verify-reset-password-otp"
        const val RESET_PASSWORD = "identity/authentication/reset-password"
    }
}