package com.spendoo.identity.data.dataSource.remote.dto.auth.request

import com.spendoo.identity.domain.entity.Gender
import com.spendoo.identity.domain.model.RegisterRequest
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
    @SerialName("deviceToken")
    val deviceToken: String? = null
)

fun RegisterRequest.toDto(deviceToken: String?) = RegisterRequestDto(
    fullName = fullName,
    email = email,
    password = password,
    birthDate = birthDate.toString(),
    gender = gender,
    deviceToken = deviceToken
)