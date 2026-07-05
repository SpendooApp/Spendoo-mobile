package com.spendoo.notifications.domain.repository

import com.spendoo.notifications.domain.entity.Notification
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData

interface NotificationRepository {
    suspend fun getNotifications(query: PageQuery): PagedData<Notification>
    suspend fun getUnreadNotificationsCount(): Long
    suspend fun markAllAsRead()
}
