package com.spendoo.goals.domain.entity

import com.spendoo.categories.domain.entity.category.CategoryIcon

data class Goal(
    val id: String,
    val name: String,
    val icon: CategoryIcon,
    val progress: Float
)