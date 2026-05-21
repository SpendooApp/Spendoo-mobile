package com.spendoo.offers.domain.entity

data class Offer(
    val id: String,
    val discountPercent: Int?,
    val imageUrl: String?
)