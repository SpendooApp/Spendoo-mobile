package com.spendoo.categories.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.spendoo.categories.data.local.dao.ExpenseTitleDao
import com.spendoo.categories.data.local.entity.ExpenseTitleEntity

@Database(entities = [ExpenseTitleEntity::class], version = 1)
@ConstructedBy(CategoriesDatabaseConstructor::class)
abstract class CategoriesDatabase : RoomDatabase() {
    abstract fun expenseTitleDao(): ExpenseTitleDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object CategoriesDatabaseConstructor : RoomDatabaseConstructor<CategoriesDatabase> {
    override fun initialize(): CategoriesDatabase
}
