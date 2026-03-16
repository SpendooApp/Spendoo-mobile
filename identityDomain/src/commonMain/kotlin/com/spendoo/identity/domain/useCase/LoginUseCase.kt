package com.spendoo.identity.domain.useCase

import com.spendoo.identity.domain.exception.InvalidPasswordException
import com.spendoo.identity.domain.repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository,
) {
    suspend fun login(email: String, password: String) {
        if (!isPasswordValid(password)) throw InvalidPasswordException()

        authenticationRepository.login(
            email = email,
            password = password
        )
    }

    fun isPasswordValid(password: String) = password.length >= PASSWORD_MIN_LENGTH

    private companion object {
        const val PASSWORD_MIN_LENGTH = 8
    }
}