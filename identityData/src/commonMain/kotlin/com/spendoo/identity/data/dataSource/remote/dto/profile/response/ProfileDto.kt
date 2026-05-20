package com.spendoo.identity.data.dataSource.remote.dto.profile.response

import com.spendoo.identity.domain.model.Profile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("id")
    val id: String,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("birthDate")
    val birthDate: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
)

fun ProfileDto.toDomain(): Profile = Profile(
    id = id,
    fullName = fullName,
    birthDate = birthDate,
    gender = gender,
    imageUrl = imageUrl,
)