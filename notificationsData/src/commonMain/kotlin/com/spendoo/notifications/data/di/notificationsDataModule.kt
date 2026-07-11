package com.spendoo.notifications.data.di

import com.spendoo.notifications.data.repository.NotificationRepositoryImpl
import com.spendoo.notifications.domain.repository.NotificationRepository
import com.spendoo.notifications.domain.scheduler.LocalNotificationScheduler
import com.spendoo.notifications.domain.scheduler.NotificationReminderManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun getLocalNotificationScheduler(): LocalNotificationScheduler

val notificationsDataModule = module {
    singleOf(::NotificationRepositoryImpl) bind NotificationRepository::class
    single<LocalNotificationScheduler> { getLocalNotificationScheduler() }
    singleOf(::NotificationReminderManager)
}
