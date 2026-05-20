package com.spendoo.categories.domain.entity.category

data class Category(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val priority: PriorityOption,
    val leftOverOption: LeftOverOption,
    val budget: Budget,
)