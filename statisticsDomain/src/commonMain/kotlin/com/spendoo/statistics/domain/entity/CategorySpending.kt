package com.spendoo.statistics.domain.entity

import com.spendoo.shared.domain.entity.CategoryIcon

data class CategorySpending(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val spending: Double,
    val percentageChange: Double,
    val contributionPercentage: Double
)
