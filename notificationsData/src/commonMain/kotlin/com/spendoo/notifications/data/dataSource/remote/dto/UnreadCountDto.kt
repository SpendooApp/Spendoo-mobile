package com.spendoo.notifications.data.dataSource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnreadCountDto(
    @SerialName("unreadCount")
    val unreadCount: Long
)
