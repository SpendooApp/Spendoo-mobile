package com.spendoo.goals.domain.entity

import com.spendoo.shared.domain.entity.CategoryIcon
import kotlinx.datetime.LocalDateTime

data class Goal(
    val id: String,
    val name: String,
    val priority: Int,
    val icon: CategoryIcon,
    val deadline: LocalDateTime,
    val currentAmount: Double,
    val targetAmount: Double,
    val savingPercentage: Int,
    val isCompleted: Boolean
)