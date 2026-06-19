package com.spendoo.categories.domain.entity.transaction

import com.spendoo.shared.domain.entity.CategoryIcon

data class CategorySpending(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val totalAmount: Double,
)