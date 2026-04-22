package com.spendoo.categories.domain.entity.transaction

data class CreateExpense(
    val entries: List<ExpenseEntry>,
)