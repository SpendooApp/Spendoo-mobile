package com.spendoo.goals.data.dataSource.remote.dto

import com.spendoo.goals.domain.entity.Achievement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AchievementDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("targetValue")
    val targetValue: Double,
    @SerialName("currentProgress")
    val currentProgress: Double,
    @SerialName("isUnlocked")
    val isUnlocked: Boolean
)

fun AchievementDto.toDomain(): Achievement = Achievement(
    id = id,
    title = title,
    description = description,
    targetValue = targetValue,
    currentProgress = currentProgress,
    isUnlocked = isUnlocked
)
