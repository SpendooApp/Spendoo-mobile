package com.spendoo.identity.data.repository

import com.mmk.kmpnotifier.KMPNotifier
import com.mmk.kmpnotifier.push.firebase.firebasePushNotifier
import com.spendoo.identity.data.dataSource.remote.dto.auth.request.toDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.response.AuthenticationResponse
import com.spendoo.identity.data.dataSource.remote.dto.auth.response.toDomain
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.OtpRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.VerifyOtpRequestDto
import com.spendoo.identity.data.utils.invalidateAuthTokens
import com.spendoo.identity.domain.model.RegisterRequest
import com.spendoo.identity.domain.repository.AuthenticationRepository
import com.spendoo.identity.domain.repository.RegisterRepository
import com.spendoo.shared.data.shared.BaseGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class RegisterRepositoryImpl(
    client: HttpClient,
    private val authenticationRepository: AuthenticationRepository
) : BaseGateway(client), RegisterRepository {

    override suspend fun reSendOTP(email: String) {
        tryToExecute<Unit> {
            post(REGISTER_REQUEST_OTP) {
                setBody(OtpRequestDto(email))
            }
        }
    }

    override suspend fun verifyOTPCode(email: String, otp: String) {
        val deviceToken = KMPNotifier.firebasePushNotifier.getToken()

        val response = tryToExecute<AuthenticationResponse> {
            post(REGISTER_VERIFY_OTP) {
                setBody(VerifyOtpRequestDto(email = email, otp = otp, deviceToken = deviceToken))
            }
        }
        authenticationRepository.saveAuthTokens(response.toDomain())
        client.invalidateAuthTokens()
    }

    override suspend fun register(request: RegisterRequest) {
        val deviceToken = KMPNotifier.firebasePushNotifier.getToken()

        tryToExecute<Unit> {
            post(REGISTER) {
                setBody(request.toDto(deviceToken))
            }
        }
    }

    companion object {
        const val REGISTER = "api/v1/identity/auth/signup"
        const val REGISTER_REQUEST_OTP = "api/v1/identity/auth/resend-otp"
        const val REGISTER_VERIFY_OTP = "api/v1/identity/auth/verify-account"
    }
}