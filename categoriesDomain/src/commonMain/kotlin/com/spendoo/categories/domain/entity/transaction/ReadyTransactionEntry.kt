package com.spendoo.categories.domain.entity.transaction

import com.spendoo.categories.domain.entity.category.CategoryIcon

data class ReadyTransactionEntry(
    val title: String,
    val amount: Double,
    val categoryId: String? = null,
    val note: String? = null,
    val categoryName: String? = null,
    val categoryIcon: CategoryIcon? = null
)
