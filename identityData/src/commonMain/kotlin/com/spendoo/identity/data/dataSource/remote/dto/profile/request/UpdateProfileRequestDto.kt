package com.spendoo.identity.data.dataSource.remote.dto.profile.request

import com.spendoo.identity.domain.model.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequestDto(
    @SerialName("fullName")
    val fullName: String,
    @SerialName("gender")
    val gender: Gender,
    @SerialName("birthDate")
    val birthDate: String
)
