package com.spendoo.identity.data.dataSource.remote.dto.subscription.request

import com.spendoo.identity.domain.model.BillingCycle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubscribePlanRequestDto(
    @SerialName("planId")
    val planId: String,
    @SerialName("billingCycle")
    val billingCycle: BillingCycle? = null
)
