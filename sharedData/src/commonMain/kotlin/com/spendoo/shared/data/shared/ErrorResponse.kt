package com.spendoo.shared.data.shared

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("status")
    val status: Int?,
    @SerialName("message")
    val message: String?
)
