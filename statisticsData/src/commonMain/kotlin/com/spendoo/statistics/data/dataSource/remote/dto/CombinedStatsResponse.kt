package com.spendoo.statistics.data.dataSource.remote.dto

import com.spendoo.statistics.domain.entity.CombinedStats
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CombinedStatsResponse(
    @SerialName("financialStats")
    val financialStats: FinancialStatsResponse,
    @SerialName("budgetStatus")
    val budgetStatus: BudgetStatusResponse,
    @SerialName("topCategories")
    val topCategories: TopCategoriesResponse
)

fun CombinedStatsResponse.toDomain(): CombinedStats {
    return CombinedStats(
        financialStats = this.financialStats.toDomain(),
        budgetStatus = this.budgetStatus.toDomain(),
        topCategories = this.topCategories.toDomain()
    )
}
