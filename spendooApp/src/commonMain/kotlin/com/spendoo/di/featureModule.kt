package com.spendoo.di

import com.spendoo.categories.data.di.categoriesDataModule
import com.spendoo.identity.domain.di.domainModule as identityDomainModule
import com.spendoo.identity.presentation.di.identityScreensModule
import com.spendoo.identity.data.di.identityDataModule
import org.koin.dsl.module

val featureModule = module {
    includes(
        identityScreensModule,
        identityDomainModule,
        identityDataModule,
        categoriesDataModule,
    )
}