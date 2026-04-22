package com.spendoo.categories.data.dataSource.remote.dto.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategorySummaryDto(
    @SerialName("totalBudget")
    val totalBudget: Double? = null,
    @SerialName("totalSpent")
    val totalSpent: Double? = null,
    @SerialName("addedIncome")
    val addedIncome: Double? = null,
)