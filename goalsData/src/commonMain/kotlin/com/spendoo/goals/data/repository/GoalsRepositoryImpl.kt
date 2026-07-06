package com.spendoo.goals.data.repository

import com.spendoo.goals.data.dataSource.remote.dto.*
import com.spendoo.goals.data.endpoint.GoalsEndpoints
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.shared.data.dataSource.remote.dto.BasePagedData
import com.spendoo.shared.data.dataSource.remote.dto.toPagedData
import com.spendoo.goals.domain.entity.Goal
import com.spendoo.goals.domain.entity.GoalsSummary
import com.spendoo.goals.domain.repository.GoalsRepository
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.appendPathSegments
import kotlinx.datetime.LocalDateTime
import com.spendoo.shared.domain.utils.toUtcInstant

class GoalsRepositoryImpl(
    client: HttpClient,
) : BaseGateway(client), GoalsRepository {

    override suspend fun getGoals(query: PageQuery): PagedData<Goal> {
        val response = tryToExecute<BasePagedData<GoalResponseDto>> {
            get(GoalsEndpoints.GOALS) {
                url {
                    parameters.append("page", query.page.toString())
                    parameters.append("size", query.size.toString())
                    query.sort?.forEach { sort -> parameters.append("sort", sort) }
                }
            }
        }
        return response.toPagedData { it.toDomain() }
    }

    override suspend fun getGoalsSummary(): GoalsSummary {
        return tryToExecute<GoalsSummaryDto> {
            get(GoalsEndpoints.GOALS_SUMMARY)
        }.toDomain()
    }

    override suspend fun createGoal(name: String, targetAmount: Double, deadline: LocalDateTime, icon: CategoryIcon, priority: Int) {
        tryToExecute<Unit> {
            post(GoalsEndpoints.GOALS) {
                setBody(GoalCreateRequestDto(name, targetAmount, deadline.toUtcInstant().toString(), icon.name, priority))
            }
        }
    }

    override suspend fun updateGoal(goalId: String, name: String, targetAmount: Double, deadline: LocalDateTime, icon: CategoryIcon, priority: Int) {
        tryToExecute<Unit> {
            patch(GoalsEndpoints.GOALS) {
                url { appendPathSegments(goalId) }
                setBody(GoalUpdateRequestDto(name, targetAmount, deadline.toUtcInstant().toString(), icon.name, priority))
            }
        }
    }

    override suspend fun deleteGoal(goalId: String) {
        tryToExecute<Unit> {
            delete(GoalsEndpoints.GOALS) {
                url { appendPathSegments(goalId) }
            }
        }
    }

    override suspend fun assignAmount(goalId: String, amount: Double) {
        tryToExecute<Unit> {
            post(GoalsEndpoints.GOALS) {
                url { appendPathSegments(goalId, "assign") }
                setBody(AssignAmountRequestDto(amount))
            }
        }
    }

    override suspend fun addToSavings(amount: Double) {
        tryToExecute<Unit> {
            post(GoalsEndpoints.ADD_TO_SAVINGS) {
                setBody(AssignAmountRequestDto(amount))
            }
        }
    }
}
