package com.spendoo.categories.data.di

import com.spendoo.categories.data.repository.CategoriesRepositoryImpl
import com.spendoo.categories.data.repository.TransactionsRepositoryImpl
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.categories.data.repository.ScheduledPaymentsRepositoryImpl
import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import org.koin.dsl.module

val categoriesDataModule = module {

    single<CategoriesRepository> {
        CategoriesRepositoryImpl(client = get())
    }

    single<TransactionsRepository> {
        TransactionsRepositoryImpl(client = get())
    }

    single<ScheduledPaymentsRepository> {
        ScheduledPaymentsRepositoryImpl(client = get())
    }
}
