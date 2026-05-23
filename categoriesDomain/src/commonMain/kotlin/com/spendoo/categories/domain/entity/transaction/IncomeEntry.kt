package com.spendoo.categories.domain.entity.transaction

import kotlinx.datetime.LocalDateTime

data class IncomeEntry(
    val title: String,
    val amount: Double,
    val transactionDate: LocalDateTime,
    val note: String? = null,
)
