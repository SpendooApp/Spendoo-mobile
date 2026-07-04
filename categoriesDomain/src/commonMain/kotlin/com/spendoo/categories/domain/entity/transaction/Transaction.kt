package com.spendoo.categories.domain.entity.transaction

import com.spendoo.categories.domain.entity.category.CategoryLite
import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: String,
    val title: String,
    val amount: Double,
    val note: String? = null,
    val date: LocalDateTime,
    val category: CategoryLite? = null,
    val type: TransactionType,
)