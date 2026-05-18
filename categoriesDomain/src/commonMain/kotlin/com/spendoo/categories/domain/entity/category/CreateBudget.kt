package com.spendoo.categories.domain.entity.category

data class CreateBudget(
    val amount: Double,
    val period: Int,
    val startDate: String,
)