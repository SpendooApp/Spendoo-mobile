package com.spendoo.identity.domain.model

data class SubscriptionPlan(
    val id: String,
    val title: String,
    val description: String,
    val code: PlanCode,
    val priceMonthly: Double,
    val priceYearly: Double,
    val isMostPopular: Boolean,
    val benefits: List<String>,
    val isSelected: Boolean
)
