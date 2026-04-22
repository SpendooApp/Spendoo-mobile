package com.spendoo.categories.domain.entity.category

import com.spendoo.categories.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.LeftOverOption

data class CreateBudget(
    val amount: Double,
    val period: Int,
    val startDate: String,
)

data class Budget(
    val amount: Double,
    val spentAmount: Double,
    val spendingPercentage: Int,
    val period: Int,
    val startDate: String,
    val endDate: String,
)

data class CreateCategory(
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val leftOverOptions: LeftOverOption,
    val priority: Int,
    val budget: CreateBudget,
)

data class UpdateCategory(
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val leftOverOptions: LeftOverOption,
    val priority: Int,
    val budget: CreateBudget,
)

data class Category(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val priority: Int,
    val leftOverOptions: LeftOverOption,
    val budget: Budget,
)

data class CategoryLite(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val priority: Int,
    val leftOverOptions: LeftOverOption,
)

data class CategorySummary(
    val totalBudget: Double? = null,
    val totalSpent: Double? = null,
    val addedIncome: Double? = null,
)

