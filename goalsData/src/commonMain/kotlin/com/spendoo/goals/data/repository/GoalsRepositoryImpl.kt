package com.spendoo.goals.data.repository

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.repository.GoalsRepository
import com.spendoo.goals.domain.utils.PageQuery
import com.spendoo.goals.domain.utils.PagedData
import kotlinx.coroutines.delay

class GoalsRepositoryImpl : GoalsRepository {
    private val mockGoals = listOf(
        Goal(
            id = "1",
            name = "Vacation Fund",
            icon = CategoryIcon.ENTERTAINMENT,
            progress = 0.5f
        ),
        Goal(
            id = "2",
            name = "New Car",
            icon = CategoryIcon.CAR,
            progress = 0.3f
        ),
        Goal(
            id = "3",
            name = "Emergency Savings",
            icon = CategoryIcon.FITNESS,
            progress = 0.8f
        )
    )

    override suspend fun getGoals(query: PageQuery): PagedData<Goal> {
        delay(2000)
        return PagedData(
            data = mockGoals,
            totalItems = mockGoals.size.toLong(),
            isLastPage = true
        )
    }
}
