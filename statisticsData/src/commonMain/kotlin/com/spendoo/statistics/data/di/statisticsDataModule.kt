package com.spendoo.statistics.data.di

import com.spendoo.statistics.data.repository.StatisticsRepositoryImpl
import com.spendoo.statistics.domain.repository.StatisticsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val statisticsDataModule = module {
    singleOf(::StatisticsRepositoryImpl) bind StatisticsRepository::class
}
