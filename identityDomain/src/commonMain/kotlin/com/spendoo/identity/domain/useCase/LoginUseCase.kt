package com.spendoo.identity.domain.useCase

import com.spendoo.identity.domain.repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository,
) {
    suspend fun login(email: String, password: String) {
        authenticationRepository.login(
            email = email,
            password = password
        )
    }
}