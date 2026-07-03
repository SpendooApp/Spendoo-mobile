package com.spendoo.di

import com.spendoo.appEntryPoint.MainEntryViewModel
import com.spendoo.AppEnvironment
import com.spendoo.designsystem.navigation.ResultStore
import com.spendoo.designsystem.navigation.SnackBarManager
import com.spendoo.designsystem.navigation.effector.Effector
import com.spendoo.designsystem.navigation.effector.EffectorImpl
import com.spendoo.designsystem.navigation.getDispatcherProvider
import com.spendoo.navigation.GlobalNavigationHandlerImpl
import com.spendoo.shared.domain.navigation.GlobalNavigationHandler
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

const val APP_VERSION = "appVersion"
val appModule = module {
    single(named(APP_VERSION)) { AppEnvironment.versionName }
    single { SnackBarManager() }
    single { getDispatcherProvider() }
    singleOf(::MainEntryViewModel)
    singleOf(::EffectorImpl) bind Effector::class
    singleOf(::ResultStore)
    singleOf(::GlobalNavigationHandlerImpl) bind GlobalNavigationHandler::class
}