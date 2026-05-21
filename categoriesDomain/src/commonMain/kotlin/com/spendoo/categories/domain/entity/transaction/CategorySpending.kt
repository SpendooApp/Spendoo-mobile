package com.spendoo.categories.domain.entity.transaction

import com.spendoo.categories.domain.entity.category.CategoryIcon

data class CategorySpending(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val totalAmount: Double,
)