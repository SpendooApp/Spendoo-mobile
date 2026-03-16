package com.spendoo.identity.data.mapper

import com.spendoo.identity.data.dataSource.remote.dto.auth.request.RegisterRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.auth.response.AuthenticationResponse
import com.spendoo.identity.domain.model.AuthenticationTokens
import com.spendoo.identity.domain.model.RegisterRequest

fun RegisterRequest.toDto() = RegisterRequestDto(
    fullName = fullName,
    email = email,
    password = password,
    birthDate = birthDate.toString(),
    gender = gender,
)

fun AuthenticationResponse.toDomain() = AuthenticationTokens(
    accessToken = accessToken,
    refreshToken = refreshToken
)