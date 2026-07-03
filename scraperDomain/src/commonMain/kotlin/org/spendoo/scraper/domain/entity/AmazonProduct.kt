package org.spendoo.scraper.domain.entity

data class AmazonProduct(
    val title: String,
    val description: String?,
    val price: Double?,
    val currency: String?,
    val rating: Double?,
    val reviews: Int?,
    val link: String,
    val imageUrl: String?
)
