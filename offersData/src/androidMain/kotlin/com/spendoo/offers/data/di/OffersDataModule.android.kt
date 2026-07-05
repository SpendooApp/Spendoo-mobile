package com.spendoo.offers.data.di

import androidx.room.Room
import com.spendoo.offers.data.local.OffersDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformOffersDataModule: Module = module {
    single<OffersDatabase> {
        val context = get<android.content.Context>()
        val dbFile = context.getDatabasePath("offers.db")
        Room.databaseBuilder<OffersDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).fallbackToDestructiveMigration(true).build()
    }
}
