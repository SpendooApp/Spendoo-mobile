package com.spendoo.identity.data.dataSource.remote.dto.profile.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageDto(
    @SerialName("imageUrl")
    val imageUrl: String,
)

