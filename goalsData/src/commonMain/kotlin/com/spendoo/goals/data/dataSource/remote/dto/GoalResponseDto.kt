package com.spendoo.goals.data.dataSource.remote.dto

import com.spendoo.goals.domain.entity.Goal
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.utils.toLocalDateTimeOrDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalResponseDto(
    @SerialName("goalId")
    val goalId: String,
    @SerialName("goalName")
    val goalName: String,
    @SerialName("priority")
    val priority: Int,
    @SerialName("goalIcon")
    val goalIcon: String,
    @SerialName("deadline")
    val deadline: String,
    @SerialName("currentAmount")
    val currentAmount: Double,
    @SerialName("targetAmount")
    val targetAmount: Double,
    @SerialName("savingPercentage")
    val savingPercentage: Int,
    @SerialName("completed")
    val completed: Boolean
)

fun GoalResponseDto.toDomain(): Goal {
    return Goal(
        id = goalId,
        name = goalName,
        priority = priority,
        icon = CategoryIcon.fromStringOrDefault(goalIcon),
        deadline = deadline.toLocalDateTimeOrDefault(),
        currentAmount = currentAmount,
        targetAmount = targetAmount,
        savingPercentage = savingPercentage,
        isCompleted = completed
    )
}
