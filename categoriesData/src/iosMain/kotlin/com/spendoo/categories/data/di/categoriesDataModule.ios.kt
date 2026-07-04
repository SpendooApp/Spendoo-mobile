package com.spendoo.categories.data.di

import androidx.room.Room
import com.spendoo.categories.data.local.CategoriesDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val platformCategoriesDataModule: Module = module {
    single<CategoriesDatabase> {
        val dbFilePath = NSHomeDirectory() + "/categories.db"
        Room.databaseBuilder<CategoriesDatabase>(
            name = dbFilePath
        ).fallbackToDestructiveMigration(true).build()
    }
}
