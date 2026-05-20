package com.spendoo.home.presentation.di

import com.spendoo.home.presentation.navigation.effector.Effector
import com.spendoo.home.presentation.navigation.effector.EffectorImpl
import com.spendoo.home.presentation.screen.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    single<Effector> { EffectorImpl() }
    viewModelOf(::HomeViewModel)
}
