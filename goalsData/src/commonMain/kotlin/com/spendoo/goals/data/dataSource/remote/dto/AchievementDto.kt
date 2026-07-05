package com.spendoo.goals.data.dataSource.remote.dto

import com.spendoo.goals.domain.entity.Achievement
import com.spendoo.goals.domain.entity.AchievementType
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
    @SerialName("level")
    val level: Long?,
    @SerialName("achievementType")
    val type: AchievementType,
    @SerialName("targetValue")
    val targetValue: Double,
    @SerialName("currentProgress")
    val currentProgress: Double,
    @SerialName("unlocked")
    val isUnlocked: Boolean
)

fun AchievementDto.toDomain(): Achievement = Achievement(
    id = id,
    title = title,
    description = description,
    level = level ?: 1,
    targetValue = targetValue,
    type = type,
    currentProgress = currentProgress,
    isUnlocked = isUnlocked
)
