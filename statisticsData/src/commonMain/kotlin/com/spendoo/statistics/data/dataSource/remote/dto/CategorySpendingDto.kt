package com.spendoo.statistics.data.dataSource.remote.dto

import com.spendoo.statistics.domain.entity.CategorySpending
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategorySpendingDto(
    @SerialName("categoryId")
    val categoryId: String,
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("categoryIcon")
    val categoryIcon: String,
    val spending: Double,
    @SerialName("percentageChange")
    val percentageChange: Double,
    @SerialName("contributionPercentage")
    val contributionPercentage: Double
)

fun CategorySpendingDto.toDomain(): CategorySpending {
    return CategorySpending(
        categoryId = this.categoryId,
        categoryName = this.categoryName,
        categoryIcon = this.categoryIcon,
        spending = this.spending,
        percentageChange = this.percentageChange,
        contributionPercentage = this.contributionPercentage
    )
}
