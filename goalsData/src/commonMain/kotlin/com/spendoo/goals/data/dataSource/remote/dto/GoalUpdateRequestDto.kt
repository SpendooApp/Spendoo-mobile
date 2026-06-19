package com.spendoo.goals.data.dataSource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalUpdateRequestDto(
    @SerialName("goalName")
    val goalName: String,
    @SerialName("targetAmount")
    val targetAmount: Double,
    @SerialName("deadline")
    val deadline: String,
    @SerialName("goalIcon")
    val goalIcon: String,
    @SerialName("priority")
    val priority: Int
)
