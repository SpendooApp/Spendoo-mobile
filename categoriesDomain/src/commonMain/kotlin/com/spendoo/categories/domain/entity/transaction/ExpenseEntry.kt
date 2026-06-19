package com.spendoo.categories.domain.entity.transaction

import kotlinx.datetime.LocalDateTime

data class ExpenseEntry(
    val title: String,
    val amount: Double,
    val categoryId: String,
    val transactionDate: LocalDateTime,
    val note: String? = null,
)