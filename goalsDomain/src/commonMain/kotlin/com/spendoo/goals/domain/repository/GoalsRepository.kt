package com.spendoo.goals.domain.repository

import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.entity.GoalsSummary
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import kotlinx.datetime.LocalDateTime

interface GoalsRepository {
    suspend fun getGoals(query: PageQuery): PagedData<Goal>
    suspend fun getGoalsSummary(): GoalsSummary
    suspend fun createGoal(name: String, targetAmount: Double, deadline: LocalDateTime, icon: CategoryIcon, priority: Int)
    suspend fun updateGoal(goalId: String, name: String, targetAmount: Double, deadline: LocalDateTime, icon: CategoryIcon, priority: Int)
    suspend fun deleteGoal(goalId: String)
    suspend fun assignAmount(goalId: String, amount: Double)
    suspend fun addToSavings(amount: Double)
}
