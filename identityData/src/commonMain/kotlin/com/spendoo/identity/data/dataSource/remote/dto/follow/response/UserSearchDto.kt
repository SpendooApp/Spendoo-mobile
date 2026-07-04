package com.spendoo.identity.data.dataSource.remote.dto.follow.response

import com.spendoo.identity.domain.model.UserSearch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSearchDto(
    @SerialName("userId")
    val userId: String,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("imageUrl")
    val imageUrl: String? = null
)

fun UserSearchDto.toDomain(): UserSearch = UserSearch(
    userId = userId,
    fullName = fullName,
    imageUrl = imageUrl
)
