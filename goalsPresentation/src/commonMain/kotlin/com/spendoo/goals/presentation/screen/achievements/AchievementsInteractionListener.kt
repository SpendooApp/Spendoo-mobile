package com.spendoo.goals.presentation.screen.achievements

import com.spendoo.goals.domain.entity.Achievement

interface AchievementsInteractionListener {
    fun onBackClicked()
    fun onAchievementClicked(achievement: Achievement)
    fun onDismissAchievementSheet()
    fun onLoadMore()
    fun onReload()
}
