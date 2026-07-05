package com.spendoo.offers.data.di

import com.spendoo.offers.data.local.OffersDatabase
import com.spendoo.offers.data.repository.OffersRepositoryImpl
import com.spendoo.offers.domain.repository.OffersRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformOffersDataModule: Module

val offersDataModule = module {
    includes(platformOffersDataModule)
    singleOf(::OffersRepositoryImpl) bind OffersRepository::class
    single { get<OffersDatabase>().offersDao() }
}
