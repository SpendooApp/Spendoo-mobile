package com.spendoo.categories.data.dataSource.remote.dto.category

import com.spendoo.categories.domain.entity.category.CategorySummary
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

fun CategorySummaryDto.toDomain(): CategorySummary {
    return CategorySummary(
        totalBudget = totalBudget ?: 0.0,
        totalSpent = totalSpent?.let { -it } ?: 0.0,
        addedIncome = addedIncome ?: 0.0,
    )
}