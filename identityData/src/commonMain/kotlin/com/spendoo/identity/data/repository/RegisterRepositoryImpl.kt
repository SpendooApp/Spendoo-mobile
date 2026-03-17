package com.spendoo.identity.data.repository

import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.OtpRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.VerifyOtpRequestDto
import com.spendoo.identity.data.mapper.toDto
import com.spendoo.identity.data.shared.BaseGateway
import com.spendoo.identity.domain.model.RegisterRequest
import com.spendoo.identity.domain.repository.RegisterRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class RegisterRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), RegisterRepository {

    override suspend fun requestOTP(email: String) {
        tryToExecute<Unit> {
            post(REGISTER_REQUEST_OTP) {
                setBody(OtpRequestDto(email))
            }
        }
    }

    override suspend fun verifyOTPCode(email: String, otp: String) {
        tryToExecute<Unit> {
            post(REGISTER_VERIFY_OTP) {
                setBody(VerifyOtpRequestDto(email = email, otp = otp))
            }
        }
    }

    override suspend fun register(request: RegisterRequest) {
        tryToExecute<String> {
            post(REGISTER) {
                setBody(request.toDto())
            }
        }
    }

    companion object {
        const val REGISTER = "api/v1/identity/auth/signup"
        const val REGISTER_REQUEST_OTP = "api/v1/identity/auth/resend-otp"
        const val REGISTER_VERIFY_OTP = "api/v1/identity/auth/verify-otp"
    }
}