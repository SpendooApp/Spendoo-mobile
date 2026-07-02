package com.spendoo.identity.data.repository

import com.mmk.kmpnotifier.KMPNotifier
import com.mmk.kmpnotifier.push.firebase.firebasePushNotifier
import com.russhwolf.settings.Settings
import com.spendoo.identity.data.dataSource.local.setting.accessToken
import com.spendoo.identity.data.dataSource.local.setting.refreshToken
import com.spendoo.identity.data.dataSource.remote.dto.auth.request.LoginRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.request.RefreshRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.request.UpdateDeviceTokenRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.response.AuthenticationResponse
import com.spendoo.identity.data.dataSource.remote.dto.auth.response.toDomain
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.shared.domain.exception.UnAuthorizedException
import com.spendoo.shared.domain.exception.UserIsBlockedException
import com.spendoo.identity.data.utils.invalidateAuthTokens
import com.spendoo.identity.domain.model.AuthenticationTokens
import com.spendoo.identity.domain.repository.AuthenticationRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class AuthenticationRepositoryImpl(
    client: HttpClient,
    private val settings: Settings,
) : BaseGateway(client), AuthenticationRepository {

    private val observableToken: MutableStateFlow<String> = MutableStateFlow(getInitialToken())

    private fun getInitialToken(): String = settings.accessToken

    override suspend fun login(email: String, password: String) {
        val deviceToken = KMPNotifier.firebasePushNotifier.getToken()

        val response = tryToExecute<AuthenticationResponse> {
            post(LOGIN_ENDPOINT) {
                setBody(LoginRequestDto(email, password, deviceToken))
            }
        }
        saveAuthTokens(response.toDomain())
        client.invalidateAuthTokens()
    }

    override suspend fun logout() {
        tryToExecute<Unit> {
            post(LOGOUT_ENDPOINT) {
                setBody(RefreshRequestDto(settings.refreshToken))
            }
        }
        clearAuthStateAfterRefreshFailure()
    }

    override suspend fun refreshAccessToken(): String {
        return withContext(NonCancellable) {
            try {
                val deviceToken = KMPNotifier.firebasePushNotifier.getToken()

                val response = tryToExecute<AuthenticationResponse> {
                    post(REFRESH_ENDPOINT) {
                        setBody(RefreshRequestDto(settings.refreshToken, deviceToken))
                    }
                }
                saveTokens(response.toDomain())
                client.invalidateAuthTokens()
                settings.accessToken
            } catch (e: UnAuthorizedException) {
                clearAuthStateAfterRefreshFailure()
                throw e
            } catch (e: UserIsBlockedException) {
                clearAuthStateAfterRefreshFailure()
                throw e
            }
        }
    }

    override fun getAccessToken(): String = settings.accessToken

    override fun getRefreshToken(): String = settings.refreshToken

    override suspend fun getAuthTokens(): AuthenticationTokens? =
        createAuthTokensIfValid(settings.accessToken, settings.refreshToken)

    private fun createAuthTokensIfValid(
        accessToken: String,
        refreshToken: String
    ): AuthenticationTokens? =
        AuthenticationTokens(accessToken, refreshToken).takeIf {
            accessToken.isNotBlank() && refreshToken.isNotBlank()
        }

    override suspend fun clearAuthTokens() {
        saveTokensToSettings(AuthenticationTokens(accessToken = "", refreshToken = ""))
        emitToken("")
    }

    override fun observeTokenChange(): StateFlow<String> = observableToken

    override suspend fun saveAuthTokens(authTokens: AuthenticationTokens) {
        saveTokens(authTokens)
    }

    private suspend fun saveTokens(authTokens: AuthenticationTokens, shouldEmit: Boolean = true) {
        saveTokensToSettings(authTokens)
        if (shouldEmit) {
            emitToken(authTokens.accessToken)
        }
    }

    private suspend fun emitToken(token: String) {
        observableToken.emit(token)
    }

    private fun saveTokensToSettings(authTokens: AuthenticationTokens) {
        settings.accessToken = authTokens.accessToken
        settings.refreshToken = authTokens.refreshToken
    }

    private suspend fun clearAuthStateAfterRefreshFailure() {
        client.invalidateAuthTokens()
        clearAuthTokens()
        KMPNotifier.firebasePushNotifier.deleteMyToken()
    }

    override suspend fun sendDeviceToken(token: String) {
        val refreshToken = settings.refreshToken
        if (refreshToken.isBlank()) return

        tryToExecute<Unit> {
            patch(DEVICE_TOKEN_ENDPOINT) {
                setBody(UpdateDeviceTokenRequestDto(refreshToken, token))
            }
        }
    }

    companion object {
        const val LOGIN_ENDPOINT = "api/v1/identity/auth/login"
        const val REFRESH_ENDPOINT = "api/v1/identity/auth/refresh"
        const val LOGOUT_ENDPOINT = "api/v1/identity/auth/logout"
        const val DEVICE_TOKEN_ENDPOINT = "api/v1/identity/auth/device-token"
    }
}