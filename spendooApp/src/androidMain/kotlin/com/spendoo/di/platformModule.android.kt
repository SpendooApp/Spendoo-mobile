package com.spendoo.di

import com.spendoo.util.AppLocalizer
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppLocalizer>(
        createdAtStart = true
    ) {
        AppLocalizer(
            context = get(),
            settingsRepository = get()
        )
    }
}
