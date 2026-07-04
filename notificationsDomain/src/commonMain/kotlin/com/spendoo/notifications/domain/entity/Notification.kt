package com.spendoo.notifications.domain.entity

import kotlinx.datetime.LocalDateTime

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val sentAt: LocalDateTime,
    val isRead: Boolean
)
