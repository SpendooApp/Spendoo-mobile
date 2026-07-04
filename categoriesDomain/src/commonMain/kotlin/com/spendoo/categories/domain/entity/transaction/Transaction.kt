package com.spendoo.categories.domain.entity.transaction

import com.spendoo.categories.domain.entity.category.CategoryLite

data class Transaction(
    val id: String,
    val title: String,
    val amount: Double,
    val note: String? = null,
    val date: String,
    val category: CategoryLite? = null,
    val type: TransactionType,
)