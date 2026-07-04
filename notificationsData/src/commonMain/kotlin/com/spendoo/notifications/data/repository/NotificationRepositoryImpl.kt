package com.spendoo.notifications.data.repository

import com.spendoo.notifications.data.dataSource.remote.dto.NotificationDto
import com.spendoo.notifications.data.dataSource.remote.dto.UnreadCountDto
import com.spendoo.notifications.data.dataSource.remote.dto.toDomain
import com.spendoo.notifications.domain.entity.Notification
import com.spendoo.notifications.domain.repository.NotificationRepository
import com.spendoo.shared.data.dataSource.remote.dto.BasePagedData
import com.spendoo.shared.data.dataSource.remote.dto.toPagedData
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch

class NotificationRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), NotificationRepository {

    override suspend fun getNotifications(query: PageQuery): PagedData<Notification> {
        val response = tryToExecute<BasePagedData<NotificationDto>> {
            get("api/v1/notifications") {
                parameter("page", query.page)
                parameter("size", query.size)
            }
        }
        return response.toPagedData { it.toDomain() }
    }

    override suspend fun getUnreadNotificationsCount(): Long {
        val response = tryToExecute<UnreadCountDto> {
            get("api/v1/notifications/unread-count")
        }
        return response.unreadCount
    }

    override suspend fun markAllAsRead() {
        tryToExecute<Unit> {
            patch("api/v1/notifications/mark-all-read")
        }
    }
}
