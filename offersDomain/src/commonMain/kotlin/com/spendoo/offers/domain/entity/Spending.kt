package com.spendoo.offers.domain.entity

data class Spending(
    val id: String,
    val categoryName: String,
    val amount: Double,
    val icon: String
)