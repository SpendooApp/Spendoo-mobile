package com.spendoo.identity.data.dataSource.remote.dto.resetPassword.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class VerifyOtpRequestDto (
    @SerialName("email")
    val email: String,
    @SerialName("otp")
    val otp: String,
    @SerialName("deviceToken")
    val deviceToken: String? = null
)