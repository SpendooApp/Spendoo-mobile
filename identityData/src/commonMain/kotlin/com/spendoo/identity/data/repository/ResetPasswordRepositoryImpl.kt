package com.spendoo.identity.data.repository

import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.OtpRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.ResetPasswordRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.VerifyOtpRequestDto
import com.spendoo.identity.data.shared.BaseGateway
import com.spendoo.identity.domain.repository.ResetPasswordRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ResetPasswordRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), ResetPasswordRepository {

    override suspend fun requestOTP(email: String) {
        tryToExecute<Unit> {
            post(RESET_PASSWORD_REQUEST_OTP) {
                setBody(OtpRequestDto(email))
            }
        }
    }

    override suspend fun verifyOTPCode(email: String, otp: String) {
        tryToExecute<Unit> {
            post(RESET_PASSWORD_VERIFY_OTP) {
                setBody(VerifyOtpRequestDto(email, otp))
            }
        }
    }

    override suspend fun resetPassword(email: String, otp: String, newPassword: String) {
        tryToExecute<Unit> {
            post(RESET_PASSWORD) {
                setBody(ResetPasswordRequestDto(email, otp, newPassword))
            }
        }
    }

    companion object {
        const val RESET_PASSWORD_REQUEST_OTP = "api/v1/identity/auth/reset-password"
        const val RESET_PASSWORD_VERIFY_OTP = "api/v1/identity/auth/verify-otp"
        const val RESET_PASSWORD = "api/v1/identity/auth/reset-password"
    }
}