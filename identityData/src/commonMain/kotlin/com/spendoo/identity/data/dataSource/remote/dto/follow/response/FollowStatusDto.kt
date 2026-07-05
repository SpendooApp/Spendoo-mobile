package com.spendoo.identity.data.dataSource.remote.dto.follow.response

import com.spendoo.identity.domain.model.FollowStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowStatusDto(
    @SerialName("isFollowing")
    val isFollowing: Boolean
)

fun FollowStatusDto.toDomain(): FollowStatus = FollowStatus(
    isFollowing = isFollowing
)
