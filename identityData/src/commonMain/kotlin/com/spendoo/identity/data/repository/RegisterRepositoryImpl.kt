package com.spendoo.identity.data.repository

import com.spendoo.identity.data.dataSource.remote.dto.auth.request.CheckUserExistenceRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.response.AuthenticationResponse
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.OtpRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request.VerifyOtpRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.resetPassword.response.OtpResponse
import com.spendoo.identity.data.mapper.toDomain
import com.spendoo.identity.data.mapper.toDto
import com.spendoo.identity.data.utils.getJsonWithBody
import com.spendoo.identity.data.utils.postJson
import com.spendoo.identity.data.utils.safeWrapper
import com.spendoo.identity.domain.exception.UsernameAlreadyExistsException
import com.spendoo.identity.domain.model.RegisterRequest
import com.spendoo.identity.domain.repository.RegisterRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode

class RegisterRepositoryImpl(
    private val client: HttpClient
) : RegisterRepository {
    private var sessionId = ""

    override suspend fun requestOTP(email: String) {
        safeWrapper {
            val response: OtpResponse = client.postJson(
                OtpRequestDto(email), REGISTER_REQUEST_OTP
            )
            sessionId = response.sessionId
        }
    }

    override suspend fun verifyOTPCode(otpCode: String) {
        safeWrapper {
            client.postJson<VerifyOtpRequestDto, Unit>(
                VerifyOtpRequestDto(otpCode, sessionId), REGISTER_VERIFY_OTP
            )
        }
    }

    override suspend fun checkUserExistence(username: String): Boolean {
        return safeWrapper {
            runCatching {
                fetchUsernameExistence(username)
            }.fold(onSuccess = { exists ->
                exists.takeUnless { it }?.let { false } ?: throw UsernameAlreadyExistsException()
            }, onFailure = { throwable ->
                handleUsernameCheckExceptionOrRethrow(throwable)
            })
        }
    }

    private suspend fun fetchUsernameExistence(username: String): Boolean {
        return client.getJsonWithBody<CheckUserExistenceRequestDto, Boolean>(
            requestDto = CheckUserExistenceRequestDto(username = username),
            path = REGISTER_CHECK_USER_EXISTENCE
        )
    }

    private fun handleUsernameCheckExceptionOrRethrow(throwable: Throwable): Nothing {
        when (throwable) {
            is ClientRequestException -> handleUsernameCheckException(throwable)
            else -> throw throwable
        }
    }

    override suspend fun register(request: RegisterRequest): com.spendoo.identity.domain.model.AuthenticationTokens {
        return safeWrapper {
            val response: AuthenticationResponse = performRegisterRequest(request)
            response.toDomain()
        }
    }

    private suspend fun performRegisterRequest(request: RegisterRequest): AuthenticationResponse {
        return AuthenticationResponse(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
//        return client.postJson(
//            request.toDto(sessionId), REGISTER
//        )
    }

    private fun handleUsernameCheckException(e: ClientRequestException): Nothing {
        when (e.response.status) {
            HttpStatusCode.Conflict -> throw UsernameAlreadyExistsException()
            else -> throw e
        }
    }

    companion object {
        const val REGISTER_REQUEST_OTP = "identity/authentication/register/request-otp"
        const val REGISTER_VERIFY_OTP = "identity/authentication/register/verify-otp"
        const val REGISTER_CHECK_USER_EXISTENCE =
            "identity/authentication/register/check-user-existence"
        const val REGISTER = "identity/authentication/register"
    }
}