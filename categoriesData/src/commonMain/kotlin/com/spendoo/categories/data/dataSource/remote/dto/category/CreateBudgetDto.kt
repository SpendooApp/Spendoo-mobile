package com.spendoo.categories.data.dataSource.remote.dto.category

import com.spendoo.categories.domain.entity.category.CreateBudget
import com.spendoo.shared.domain.utils.toUtcInstant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBudgetDto(
    @SerialName("amount")
    val amount: Double,
    @SerialName("period")
    val period: Int,
    @SerialName("startDate")
    val startDate: String,
)

fun CreateBudget.toDto(): CreateBudgetDto =
    CreateBudgetDto(amount, period, LocalDateTime(startDate, LocalTime(0, 0, 0)).toUtcInstant().toString())