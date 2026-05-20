package com.spendoo.categories.domain.entity.category

data class CategoryLite(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val priority: Int,
    val leftOverOptions: LeftOverOption,
)