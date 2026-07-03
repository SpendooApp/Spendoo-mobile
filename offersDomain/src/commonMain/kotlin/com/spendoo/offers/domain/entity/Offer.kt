package com.spendoo.offers.domain.entity

data class Offer(
    val id: String,
    val discountPercent: Int? = null,
    val imageUrl: String? = null,
    val title: String? = null,
    val price: Double? = null,
    val currency: String? = null,
    val link: String? = null
)