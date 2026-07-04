package com.spendoo.goals.domain.entity

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val targetValue: Double,
    val currentProgress: Double,
    val isUnlocked: Boolean
)
