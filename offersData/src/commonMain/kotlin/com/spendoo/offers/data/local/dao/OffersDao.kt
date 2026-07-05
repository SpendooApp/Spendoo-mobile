package com.spendoo.offers.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spendoo.offers.data.local.entity.KeywordCacheEntity
import com.spendoo.offers.data.local.entity.OfferEntity

@Dao
interface OffersDao {
    @Query("SELECT * FROM offers WHERE keyword IN (:keywords) AND language = :language ORDER BY price ASC LIMIT :limit OFFSET :offset")
    suspend fun getOffersForKeywords(
        keywords: List<String>,
        language: String,
        limit: Int,
        offset: Int
    ): List<OfferEntity>

    @Query("SELECT COUNT(*) FROM offers WHERE keyword IN (:keywords) AND language = :language")
    suspend fun getOffersCountForKeywords(keywords: List<String>, language: String): Long


    @Query("SELECT * FROM keyword_cache WHERE keyword IN (:keywords) AND language = :language")
    suspend fun getCacheStatusForKeywords(keywords: List<String>, language: String): List<KeywordCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOffers(offers: List<OfferEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeywordCacheStatus(statuses: List<KeywordCacheEntity>)

    @Query("DELETE FROM offers WHERE keyword IN (:keywords) AND language = :language")
    suspend fun deleteOffersForKeywords(keywords: List<String>, language: String)
}
