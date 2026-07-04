package com.spendoo.categories.domain.entity.transaction

import com.spendoo.shared.domain.entity.CategoryIcon

data class FrequencyItem(
    val itemName: String,
    val frequency: Long,
    val totalAmount: Double,
    val categoryId: String,
    val categoryIcon: CategoryIcon,
)
