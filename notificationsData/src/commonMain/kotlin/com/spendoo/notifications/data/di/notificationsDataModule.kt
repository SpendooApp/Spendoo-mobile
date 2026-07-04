package com.spendoo.notifications.data.di

import com.spendoo.notifications.data.repository.NotificationRepositoryImpl
import com.spendoo.notifications.domain.repository.NotificationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val notificationsDataModule = module {
    singleOf(::NotificationRepositoryImpl) bind NotificationRepository::class
}
