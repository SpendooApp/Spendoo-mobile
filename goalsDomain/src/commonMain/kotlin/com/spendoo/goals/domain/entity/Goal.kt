package com.spendoo.goals.domain.entity

import kotlinx.datetime.LocalDateTime

data class Goal(
    val id: String,
    val name: String,
    val priority: Int,
    val icon: GoalIcon,
    val deadline: LocalDateTime,
    val currentAmount: Double,
    val targetAmount: Double,
    val savingPercentage: Int,
    val isCompleted: Boolean
)