package com.spendoo.identity.data.dataSource.remote.dto.subscription.response

import com.spendoo.identity.domain.model.PlanCode
import com.spendoo.identity.domain.model.SubscriptionPlan
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionPlanDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("code")
    val code: PlanCode,
    @SerialName("priceMonthly")
    val priceMonthly: Double,
    @SerialName("priceYearly")
    val priceYearly: Double,
    @SerialName("isMostPopular")
    val isMostPopular: Boolean,
    @SerialName("benefits")
    val benefits: List<String>,
    @SerialName("isSelected")
    val isSelected: Boolean
)

fun SubscriptionPlanDto.toDomain(): SubscriptionPlan = SubscriptionPlan(
    id = id,
    title = title,
    description = description,
    code = code,
    priceMonthly = priceMonthly,
    priceYearly = priceYearly,
    isMostPopular = isMostPopular,
    benefits = benefits,
    isSelected = isSelected
)
