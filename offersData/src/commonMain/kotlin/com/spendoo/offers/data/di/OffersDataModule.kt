package com.spendoo.offers.data.di

import com.spendoo.offers.data.repository.OffersRepositoryImpl
import com.spendoo.offers.domain.repository.OffersRepository
import org.koin.dsl.module

val offersDataModule = module {
    single<OffersRepository> { OffersRepositoryImpl() }
}
