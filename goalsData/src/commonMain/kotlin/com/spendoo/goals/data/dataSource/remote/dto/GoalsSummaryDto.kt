package com.spendoo.goals.data.dataSource.remote.dto

import com.spendoo.goals.domain.entity.GoalsSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalsSummaryDto(
    @SerialName("totalSaved")
    val totalSaved: Double,
    @SerialName("totalTarget")
    val totalTarget: Double,
    @SerialName("unassignedAmount")
    val unassignedAmount: Double
)

fun GoalsSummaryDto.toDomain(): GoalsSummary {
    return GoalsSummary(
        totalSaved = totalSaved,
        totalTarget = totalTarget,
        unassignedAmount = unassignedAmount
    )
}
