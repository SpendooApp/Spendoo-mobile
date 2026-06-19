package com.spendoo.goals.data.shared

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String? = null,
)