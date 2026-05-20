package com.spendoo.categories.domain.entity.category

import kotlinx.datetime.LocalDate

data class CreateBudget(
    val amount: Double,
    val period: Int,
    val startDate: LocalDate,
)