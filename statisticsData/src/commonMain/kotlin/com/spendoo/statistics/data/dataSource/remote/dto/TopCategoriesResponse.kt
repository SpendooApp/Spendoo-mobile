package com.spendoo.statistics.data.dataSource.remote.dto

import com.spendoo.statistics.domain.entity.TopCategories
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopCategoriesResponse(
    @SerialName("totalSpending")
    val totalSpending: Double,
    @SerialName("topCategories")
    val topCategories: List<CategorySpendingDto>
)

fun TopCategoriesResponse.toDomain(): TopCategories {
    return TopCategories(
        totalSpending = this.totalSpending,
        topCategories = this.topCategories.map { it.toDomain() }
    )
}
