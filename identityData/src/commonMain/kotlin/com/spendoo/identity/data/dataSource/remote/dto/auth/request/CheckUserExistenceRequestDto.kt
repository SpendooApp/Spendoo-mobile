package com.spendoo.identity.data.dataSource.remote.dto.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckUserExistenceRequestDto(
    @SerialName("username")
    val username: String
)