package com.spendoo.goals.domain.repository

import com.spendoo.goals.domain.entity.Achievement
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData

interface AchievementRepository {
    suspend fun getAchievements(query: PageQuery): PagedData<Achievement>
}
