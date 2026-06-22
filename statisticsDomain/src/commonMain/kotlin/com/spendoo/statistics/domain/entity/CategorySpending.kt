package com.spendoo.statistics.domain.entity

data class CategorySpending(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: String,
    val spending: Double,
    val percentageChange: Double,
    val contributionPercentage: Double
)
