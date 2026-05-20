package com.spendoo.categories.domain.entity.transaction

data class UpdateTransaction(
    val title: String,
    val transactionDate: String,
    val note: String? = null,
    val amount: Double,
    val categoryId: String? = null,
)