package com.spendoo.notifications.data.dataSource.remote.dto

import com.spendoo.notifications.domain.entity.Notification
import com.spendoo.notifications.domain.entity.NotificationType
import com.spendoo.shared.domain.utils.toLocalDateTimeOrDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("message")
    val message: String,
    @SerialName("type")
    val type: NotificationType,
    @SerialName("sentAt")
    val sentAt: String,
    @SerialName("read")
    val isRead: Boolean
)

fun NotificationDto.toDomain(): Notification = Notification(
    id = id,
    title = title,
    message = message,
    type = type,
    sentAt = sentAt.toLocalDateTimeOrDefault(),
    isRead = isRead
)
