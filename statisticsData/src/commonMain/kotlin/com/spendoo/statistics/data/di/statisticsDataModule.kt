package com.spendoo.statistics.data.di

import com.spendoo.statistics.data.repository.StatisticsRepositoryImpl
import com.spendoo.statistics.domain.repository.StatisticsRepository
import org.koin.dsl.module

val statisticsDataModule = module {
    single<StatisticsRepository> {
        StatisticsRepositoryImpl(client = get())
    }
}
