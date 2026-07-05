package com.spendoo.identity.data.dataSource.remote.dto.follow.response

import com.spendoo.identity.domain.model.FollowCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowCodeDto(
    @SerialName("code")
    val code: String
)

fun FollowCodeDto.toDomain(): FollowCode = FollowCode(
    code = code
)
