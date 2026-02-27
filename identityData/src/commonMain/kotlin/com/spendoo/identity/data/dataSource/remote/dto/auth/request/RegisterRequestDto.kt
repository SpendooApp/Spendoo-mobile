package com.spendoo.identity.data.dataSource.remote.dto.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    @SerialName("email")
    val email: String,
    @SerialName("username")
    val username: String,
    @SerialName("birthDate")
    val birthDate: String,
    @SerialName("gender")
    val gender: Int,
    @SerialName("password")
    val password: String,
    @SerialName("sessionId")
    val sessionId: String
)