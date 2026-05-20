package com.spendoo.categories.domain.entity.transaction

data class ExpenseEntry(
    val title: String,
    val amount: Double,
    val categoryId: String,
    val transactionDate: String,
    val note: String? = null,
)