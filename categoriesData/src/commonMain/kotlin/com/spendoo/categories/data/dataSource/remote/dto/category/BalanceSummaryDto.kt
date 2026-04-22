package com.spendoo.categories.data.dataSource.remote.dto.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalanceSummaryDto(
    @SerialName("totalBalance")
    val totalBalance: Double,
    @SerialName("income")
    val income: Double,
    @SerialName("expenses")
    val expenses: Double,
)