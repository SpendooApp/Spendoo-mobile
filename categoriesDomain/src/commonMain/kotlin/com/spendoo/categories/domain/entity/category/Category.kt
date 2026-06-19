package com.spendoo.categories.domain.entity.category

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.entity.PriorityOption

data class Category(
    val categoryId: String,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val priority: PriorityOption,
    val leftOverOption: LeftOverOption,
    val budget: Budget,
)