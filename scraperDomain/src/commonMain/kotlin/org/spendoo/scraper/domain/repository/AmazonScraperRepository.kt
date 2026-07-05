package org.spendoo.scraper.domain.repository

import org.spendoo.scraper.domain.entity.AmazonProduct

interface AmazonScraperRepository {
    suspend fun search(
        query: List<String>,
        key: String? = null,
        country: String = "com",
        language: String = "en-US",
        sortBy: String? = null,
        offersOnly: Boolean = false,
        minPrice: Double? = null,
        maxPrice: Double? = null
    ): List<AmazonProduct>

    suspend fun search(
        query: String,
        key: String? = null,
        country: String = "com",
        language: String = "en-US",
        sortBy: String? = null,
        offersOnly: Boolean = false,
        minPrice: Double? = null,
        maxPrice: Double? = null
    ): List<AmazonProduct>
}
