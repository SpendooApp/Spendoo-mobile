package com.spendoo.home.presentation.di

import com.spendoo.home.presentation.screen.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

import com.spendoo.home.presentation.screen.notifications.NotificationsViewModel

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::NotificationsViewModel)
}
