package com.spendoo.categories.data.dataSource.remote.dto.category

import com.spendoo.categories.data.mapper.toLocalDateTimeOrDefault
import com.spendoo.categories.domain.entity.category.Budget
import com.spendoo.categories.domain.entity.category.toResetCycleOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetDto(
    @SerialName("amount")
    val amount: Double,
    @SerialName("spentAmount")
    val spentAmount: Double,
    @SerialName("spendingPercentage")
    val spendingPercentage: Int,
    @SerialName("period")
    val period: Int,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
)

fun BudgetDto.toDomain(): Budget = Budget(
    amount = amount,
    spentAmount = spentAmount,
    spendingPercentage = spendingPercentage,
    period = period.toResetCycleOption(),
    startDate = startDate.toLocalDateTimeOrDefault(),
    endDate = endDate.toLocalDateTimeOrDefault(),
)