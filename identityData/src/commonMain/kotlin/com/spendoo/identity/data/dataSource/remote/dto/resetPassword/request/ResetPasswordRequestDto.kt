package com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequestDto(
    @SerialName("email")
    val email: String,
    @SerialName("otp")
    val otp: String,
    @SerialName("newPassword")
    val newPassword: String
)