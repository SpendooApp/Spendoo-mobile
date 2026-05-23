package com.spendoo.categories.domain.entity.transaction

data class ReadyTransactionEntry(
    val title: String,
    val amount: Double,
    val categoryId: String? = null,
    val note: String? = null
)
