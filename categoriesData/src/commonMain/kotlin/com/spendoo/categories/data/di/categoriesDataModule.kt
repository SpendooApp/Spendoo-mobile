package com.spendoo.categories.data.di

import com.spendoo.categories.data.local.CategoriesDatabase
import com.spendoo.categories.data.repository.CategoriesRepositoryImpl
import com.spendoo.categories.data.repository.ScheduledPaymentsRepositoryImpl
import com.spendoo.categories.data.repository.TransactionsRepositoryImpl
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import com.spendoo.categories.domain.repository.TransactionsRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCategoriesDataModule: Module

val categoriesDataModule = module {
    includes(platformCategoriesDataModule)
    singleOf(::CategoriesRepositoryImpl) bind CategoriesRepository::class
    singleOf(::TransactionsRepositoryImpl) bind TransactionsRepository::class
    singleOf(::ScheduledPaymentsRepositoryImpl) bind ScheduledPaymentsRepository::class
    single { get<CategoriesDatabase>().expenseTitleDao() }
}
