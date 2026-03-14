package com.spendoo.identity.data.dataSource.remote.dto.auth.request

import com.spendoo.identity.domain.entity.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    @SerialName("fullName")
    val fullName: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("birthDate")
    val birthDate: String,
    @SerialName("gender")
    val gender: Gender,
)