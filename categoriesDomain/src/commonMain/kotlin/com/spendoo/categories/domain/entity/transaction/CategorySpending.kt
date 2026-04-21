package com.spendoo.categories.domain.entity.transaction

import com.spendoo.categories.domain.entity.CategoryIcon

data class CategorySpending(
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val totalAmount: Double,
)