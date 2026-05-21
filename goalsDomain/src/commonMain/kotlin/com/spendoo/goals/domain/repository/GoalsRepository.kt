package com.spendoo.goals.domain.repository

import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.utils.PageQuery
import com.spendoo.goals.domain.utils.PagedData

interface GoalsRepository {
    suspend fun getGoals(query: PageQuery): PagedData<Goal>
}
