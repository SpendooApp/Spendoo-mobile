package com.spendoo.notifications.domain.scheduler

import com.spendoo.notifications.domain.entity.NotificationType

interface LocalNotificationScheduler {
    fun schedule(
        id: Int,
        title: String,
        body: String,
        delayMs: Long,
        notificationType: NotificationType
    )
    fun cancelAll(startId: Int, endId: Int)
}
