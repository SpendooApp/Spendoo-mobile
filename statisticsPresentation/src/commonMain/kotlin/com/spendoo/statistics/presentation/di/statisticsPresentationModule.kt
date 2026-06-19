package com.spendoo.statistics.presentation.di

import com.spendoo.statistics.presentation.screen.statistics.StatisticsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val statisticsPresentationModule = module {
    viewModelOf(::StatisticsViewModel)
}
