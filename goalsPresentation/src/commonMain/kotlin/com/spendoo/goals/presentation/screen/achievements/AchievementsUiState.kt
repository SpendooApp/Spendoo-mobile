package com.spendoo.goals.presentation.screen.achievements

import com.spendoo.goals.domain.entity.Achievement
import com.spendoo.goals.domain.entity.AchievementType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.achievement_categories
import spendoo.designsystem.generated.resources.achievement_goals
import spendoo.designsystem.generated.resources.achievement_login_streak
import spendoo.designsystem.generated.resources.achievement_savings
import spendoo.designsystem.generated.resources.achievement_transaction_streak
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_repeat
import spendoo.designsystem.generated.resources.ic_target
import spendoo.designsystem.generated.resources.ic_thunder
import spendoo.designsystem.generated.resources.img_coin

data class AchievementsUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val achievements: List<Achievement> = emptyList(),
    val isRefreshing: Boolean = false,
    val selectedAchievement: Achievement? = null
)

fun AchievementType.toName(): StringResource = when (this) {
    AchievementType.SAVINGS -> Res.string.achievement_savings
    AchievementType.GOALS -> Res.string.achievement_goals
    AchievementType.LOGIN_STREAK -> Res.string.achievement_login_streak
    AchievementType.TRANSACTION_STREAK -> Res.string.achievement_transaction_streak
    AchievementType.CATEGORIES -> Res.string.achievement_categories
}

fun AchievementType.toImage(): DrawableResource = when (this) {
    AchievementType.SAVINGS -> Res.drawable.img_coin
    AchievementType.GOALS -> Res.drawable.ic_target
    AchievementType.LOGIN_STREAK -> Res.drawable.ic_thunder
    AchievementType.TRANSACTION_STREAK -> Res.drawable.ic_repeat
    AchievementType.CATEGORIES -> Res.drawable.ic_categories
}