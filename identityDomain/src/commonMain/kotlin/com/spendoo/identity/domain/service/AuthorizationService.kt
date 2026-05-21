package com.spendoo.identity.domain.service

import kotlinx.coroutines.flow.StateFlow
import com.spendoo.identity.domain.repository.AuthenticationRepository

class AuthorizationService(private val authenticationRepository: AuthenticationRepository) {

    fun getAccessToken(): String {
        return authenticationRepository.getAccessToken()
    }

    suspend fun getNewAccessToken(): String {
        return authenticationRepository.refreshAccessToken()
    }

    fun getRefreshToken(): String {
        return authenticationRepository.getRefreshToken() ?: ""
    }

    fun observeAccessToken(): StateFlow<String> = authenticationRepository.observeTokenChange()
}