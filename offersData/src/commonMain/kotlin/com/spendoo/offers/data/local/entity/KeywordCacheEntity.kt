package com.spendoo.offers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "keyword_cache",
    primaryKeys = ["keyword", "language"]
)
data class KeywordCacheEntity(
    val keyword: String,
    val language: String,
    val lastFetchTime: Long
)
