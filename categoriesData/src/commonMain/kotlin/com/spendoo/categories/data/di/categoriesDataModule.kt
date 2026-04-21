package com.spendoo.categories.data.di

import com.spendoo.categories.data.repository.CategoriesRepositoryImpl
import com.spendoo.categories.data.repository.TransactionsRepositoryImpl
import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.identity.domain.service.AuthorizationService
import io.ktor.client.engine.cio.CIO
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val CATEGORIES_ENGINE = "CategoriesEngine"
private const val CATEGORIES_CLIENT = "CategoriesClient"
private const val BASE_URL = "baseUrl"

val categoriesDataModule = module {
    single(named(CATEGORIES_ENGINE)) { CIO.create() }

    single<CategoriesRepository> {
        CategoriesRepositoryImpl(client = get(named(CATEGORIES_CLIENT)))
    }

    single<TransactionsRepository> {
        TransactionsRepositoryImpl(client = get(named(CATEGORIES_CLIENT)))
    }

    single(named(CATEGORIES_CLIENT)) {
        provideCategoriesHttpClient(
            engine = get(named(CATEGORIES_ENGINE)),
            baseUrl = get(named(BASE_URL)),
            authorizationService = { get<AuthorizationService>() },
        )
    }
}

