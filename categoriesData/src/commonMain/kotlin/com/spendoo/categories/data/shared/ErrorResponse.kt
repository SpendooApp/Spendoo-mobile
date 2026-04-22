package com.spendoo.categories.data.shared

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String? = null,
)

