package com.spendoo.goals.data.dataSource.remote.dto

import com.spendoo.goals.data.mapper.toLocalDateTimeOrDefault
import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.entity.GoalIcon
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
        icon = GoalIcon.fromStringOrDefault(goalIcon),
        deadline = deadline.toLocalDateTimeOrDefault(),
        currentAmount = currentAmount,
        targetAmount = targetAmount,
        savingPercentage = savingPercentage,
        isCompleted = completed
    )
}
