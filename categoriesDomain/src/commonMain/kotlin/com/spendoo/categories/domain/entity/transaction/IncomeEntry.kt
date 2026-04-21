package com.spendoo.categories.domain.entity.transaction

data class IncomeEntry(
    val title: String,
    val amount: Double,
    val transactionDate: String,
    val note: String? = null,
)
