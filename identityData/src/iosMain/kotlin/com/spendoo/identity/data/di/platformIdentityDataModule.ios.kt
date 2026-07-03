package com.spendoo.identity.data.di

import com.spendoo.identity.domain.util.AppLocalizer
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformIdentityDataModule: Module = module {
    single<AppLocalizer>(createdAtStart = true) {
        AppLocalizer(
            settingsRepository = get()
        )
    }
}
