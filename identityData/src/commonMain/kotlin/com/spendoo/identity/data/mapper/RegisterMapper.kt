package com.spendoo.identity.data.mapper

import com.spendoo.identity.data.dataSource.remote.dto.auth.request.RegisterRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.response.AuthenticationResponse
import com.spendoo.identity.domain.entity.Gender
import com.spendoo.identity.domain.model.AuthenticationTokens
import com.spendoo.identity.domain.model.RegisterRequest

fun RegisterRequest.toDto(sessionId: String) = RegisterRequestDto(
    email = email,
    username = username,
    birthDate = birthDate.toString(),
    gender = gender.toInt(),
    password = password,
    sessionId = sessionId
)

fun AuthenticationResponse.toDomain() = AuthenticationTokens(
    accessToken = accessToken,
    refreshToken = refreshToken
)

private fun Gender.toInt() = when (this) {
    Gender.MALE -> 1
    Gender.FEMALE -> 2
}