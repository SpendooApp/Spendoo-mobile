package com.spendoo.goals.domain.repository

import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.entity.GoalsSummary
import com.spendoo.goals.domain.entity.GoalIcon
import com.spendoo.goals.domain.utils.PageQuery
import com.spendoo.goals.domain.utils.PagedData
import kotlinx.datetime.LocalDateTime

interface GoalsRepository {
    suspend fun getGoals(query: PageQuery): PagedData<Goal>
    suspend fun getGoalsSummary(): GoalsSummary
    suspend fun createGoal(name: String, targetAmount: Double, deadline: LocalDateTime, icon: GoalIcon, priority: Int)
    suspend fun updateGoal(goalId: String, name: String, targetAmount: Double, deadline: LocalDateTime, icon: GoalIcon, priority: Int)
    suspend fun deleteGoal(goalId: String)
    suspend fun assignAmount(goalId: String, amount: Double)
    suspend fun addToSavings(amount: Double)
}
