package com.spendoo.offers.data.di

import androidx.room.Room
import com.spendoo.offers.data.local.OffersDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val platformOffersDataModule: Module = module {
    single<OffersDatabase> {
        val dbFilePath = NSHomeDirectory() + "/offers.db"
        Room.databaseBuilder<OffersDatabase>(
            name = dbFilePath
        ).fallbackToDestructiveMigration(true).build()
    }
}
