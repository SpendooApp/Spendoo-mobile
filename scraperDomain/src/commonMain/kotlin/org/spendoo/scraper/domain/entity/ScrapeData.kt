package org.spendoo.scraper.domain.entity

data class ScrapeData(
    val count: Int,
    val results: List<AmazonProduct>
)

data class ScrapeResponse(
    val data: ScrapeData?,
    val error: String?
)

data class ScrapeRequest(
    val query: String,
    val country: String = "com",
    val language: String = "en-US",
    val sortBy: String? = null,
    val offersOnly: Boolean = false,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val maxItems: Int? = null
)
