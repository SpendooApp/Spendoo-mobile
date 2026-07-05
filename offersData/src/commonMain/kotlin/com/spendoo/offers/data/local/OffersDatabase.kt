package com.spendoo.offers.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.spendoo.offers.data.local.dao.OffersDao
import com.spendoo.offers.data.local.entity.KeywordCacheEntity
import com.spendoo.offers.data.local.entity.OfferEntity

@Database(entities = [OfferEntity::class, KeywordCacheEntity::class], version = 2)
@ConstructedBy(OffersDatabaseConstructor::class)
abstract class OffersDatabase : RoomDatabase() {
    abstract fun offersDao(): OffersDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object OffersDatabaseConstructor : RoomDatabaseConstructor<OffersDatabase> {
    override fun initialize(): OffersDatabase
}
