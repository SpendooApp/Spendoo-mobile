package com.spendoo.di

import com.spendoo.identity.domain.di.domainModule
import com.spendoo.identity.presentation.di.identityScreensModule
import com.spendoo.identity.data.di.identityDataModule
import org.koin.dsl.module

val featureModule = module {
    includes(
        identityScreensModule,
        domainModule,
        identityDataModule,
        )
}