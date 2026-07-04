package com.spendoo.categories.data.di

import androidx.room.Room
import com.spendoo.categories.data.local.CategoriesDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformCategoriesDataModule: Module = module {
    single<CategoriesDatabase> {
        val context = get<android.content.Context>()
        val dbFile = context.getDatabasePath("categories.db")
        Room.databaseBuilder<CategoriesDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).fallbackToDestructiveMigration(true).build()
    }
}
