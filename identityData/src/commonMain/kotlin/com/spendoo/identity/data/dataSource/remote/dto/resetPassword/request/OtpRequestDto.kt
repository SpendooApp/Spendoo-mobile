package com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpRequestDto(
    @SerialName("email")
    val email: String
)