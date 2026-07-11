package com.spendoo.offers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offers")
data class OfferEntity(
    @PrimaryKey val id: String,
    val keyword: String,
    val language: String,
    val discountPercent: Int?,
    val imageUrl: String?,
    val title: String?,
    val price: Double?,
    val currency: String?,
    val rating: Double?,
    val link: String?
)
