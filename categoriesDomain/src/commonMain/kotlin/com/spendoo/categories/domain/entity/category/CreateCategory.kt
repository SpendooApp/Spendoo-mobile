package com.spendoo.categories.domain.entity.category

data class CreateCategory(
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val leftOverOptions: LeftOverOption,
    val priority: Int,
    val budget: CreateBudget,
)