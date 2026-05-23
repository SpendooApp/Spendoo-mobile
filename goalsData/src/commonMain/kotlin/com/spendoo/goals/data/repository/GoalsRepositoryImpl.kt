package com.spendoo.goals.data.repository

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.goals.data.endpoint.GoalsEndpoints
import com.spendoo.goals.data.shared.BaseGateway
import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.repository.GoalsRepository
import com.spendoo.goals.domain.utils.PageQuery
import com.spendoo.goals.domain.utils.PagedData
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.delay

class GoalsRepositoryImpl(
    client: HttpClient,
) : BaseGateway(client), GoalsRepository {
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

    override suspend fun addToSaving(amount: Double) {
//        tryToExecute<Unit> {
//            post(GoalsEndpoints.ADD_TO_SAVING) {
//                setBody(mapOf("amount" to amount))
//            }
//        }
        //TODO: Implement addToSaving endpoint and remove mock implementation
         delay(1000)
    }
}
