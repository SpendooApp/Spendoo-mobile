package com.spendoo.statistics.domain.entity

data class TopCategories(
    val totalSpending: Double,
    val topCategories: List<CategorySpending>
)
