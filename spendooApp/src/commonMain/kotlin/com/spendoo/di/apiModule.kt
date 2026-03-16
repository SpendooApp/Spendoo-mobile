package com.spendoo.di

import com.spendoo.identity.api.IdentityFeatureApi
import com.spendoo.identity.presentation.api.IdentityFeatureApiImpl
import com.spendoo.home.api.HomeFeatureApi
import com.spendoo.home.presentation.api.HomeFeatureApiImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val apiModule = module {
    singleOf(::IdentityFeatureApiImpl) bind IdentityFeatureApi::class
    singleOf(::HomeFeatureApiImpl) bind HomeFeatureApi::class
}