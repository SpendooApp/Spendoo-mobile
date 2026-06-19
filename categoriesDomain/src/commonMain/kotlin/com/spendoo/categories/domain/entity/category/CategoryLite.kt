package com.spendoo.categories.domain.entity.category

import com.spendoo.shared.domain.entity.CategoryIcon

data class CategoryLite(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val priority: Int,
    val leftOverOptions: LeftOverOption,
)