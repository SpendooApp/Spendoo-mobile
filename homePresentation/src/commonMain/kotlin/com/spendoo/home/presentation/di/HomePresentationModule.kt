package com.spendoo.home.presentation.di

import com.spendoo.home.presentation.screen.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)
}
