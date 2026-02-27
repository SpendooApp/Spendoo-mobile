package com.spendoo

import com.spendoo.di.apiModule
import com.spendoo.di.appModule
import com.spendoo.di.featureModule
import com.spendoo.di.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)

        modules(
            modules = appModule + apiModule + featureModule + networkModule
        )
    }
}