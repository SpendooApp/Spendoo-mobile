package com.spendoo.di

import com.spendoo.appEntryPoint.MainEntryViewModel
import com.spendoo.AppEnvironment
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val APP_VERSION = "appVersion"
val appModule = module {
    single(named(APP_VERSION)) { AppEnvironment.versionName }
    singleOf(::MainEntryViewModel)
}