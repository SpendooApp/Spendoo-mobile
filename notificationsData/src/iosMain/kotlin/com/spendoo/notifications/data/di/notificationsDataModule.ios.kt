package com.spendoo.notifications.data.di

import com.spendoo.notifications.domain.scheduler.LocalNotificationScheduler
import com.spendoo.notifications.data.scheduler.LocalNotificationSchedulerImpl

actual fun getLocalNotificationScheduler(): LocalNotificationScheduler {
    return LocalNotificationSchedulerImpl()
}
