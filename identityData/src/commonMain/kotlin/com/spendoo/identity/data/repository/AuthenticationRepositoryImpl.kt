package com.spendoo.identity.data.repository

import com.russhwolf.settings.Settings
import com.spendoo.identity.data.dataSource.local.setting.accessToken
import com.spendoo.identity.data.dataSource.local.setting.refreshToken
import com.spendoo.identity.data.dataSource.remote.dto.auth.request.LoginRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.request.RefreshRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.response.AuthenticationResponse
import com.spendoo.identity.data.mapper.toDomain
import com.spendoo.identity.data.utils.invalidateAuthTokens
import com.spendoo.identity.data.utils.postEmpty
import com.spendoo.identity.data.utils.postJson
import com.spendoo.identity.data.utils.safeWrapper
import com.spendoo.identity.domain.model.AuthenticationTokens
import com.spendoo.identity.domain.repository.AuthenticationRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthenticationRepositoryImpl(
    private val client: HttpClient,
    private val settings: Settings,
) : AuthenticationRepository {

    private val observableToken: MutableStateFlow<String> = MutableStateFlow(
        getInitialToken()
    )

    private fun getInitialToken(): String = settings.accessToken

    override suspend fun login(email: String, password: String) = safeWrapper {
        val response: AuthenticationResponse = client.postJson(
            LoginRequestDto(email, password),
            LOGIN_ENDPOINT
        )
        saveAuthTokens(response.toDomain())
        try {
            client.invalidateAuthTokens()
        } catch (_: Exception) {
        }
    }

    override suspend fun logout() {
        safeWrapper {
            client.postEmpty(LOGOUT_ENDPOINT)
        }
        try {
            client.invalidateAuthTokens()
        } catch (_: Exception) {
        }
        clearAuthTokens()
    }

    override suspend fun refreshAccessToken(): String {
        val response: AuthenticationResponse = safeWrapper {
            client.postJson(
                RefreshRequestDto(settings.refreshToken),
                REFRESH_ENDPOINT
            )
        }
        saveTokens(response.toDomain())
        try {
            client.invalidateAuthTokens()
        } catch (_: Exception) {
        }
        return settings.accessToken
    }

    override suspend fun getAccessToken(): String = settings.accessToken

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
        saveTokensToSettings(createEmptyTokens())
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

    private fun createEmptyTokens() = AuthenticationTokens(
        accessToken = "",
        refreshToken = ""
    )

    companion object {
        const val LOGIN_ENDPOINT = "identity/authentication/login"
        const val REFRESH_ENDPOINT = "identity/authentication/refresh"
        const val LOGOUT_ENDPOINT = "identity/authentication/logout"
    }
}