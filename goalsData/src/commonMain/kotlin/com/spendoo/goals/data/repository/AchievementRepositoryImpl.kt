package com.spendoo.goals.data.repository

import com.spendoo.goals.data.dataSource.remote.dto.AchievementDto
import com.spendoo.goals.data.dataSource.remote.dto.toDomain
import com.spendoo.goals.domain.entity.Achievement
import com.spendoo.goals.domain.repository.AchievementRepository
import com.spendoo.shared.data.dataSource.remote.dto.BasePagedData
import com.spendoo.shared.data.dataSource.remote.dto.toPagedData
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class AchievementRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), AchievementRepository {

    override suspend fun getAchievements(query: PageQuery): PagedData<Achievement> {
        val response = tryToExecute<BasePagedData<AchievementDto>> {
            get("api/v1/goals/achievements") {
                parameter("page", query.page)
                parameter("size", query.size)
            }
        }
        return response.toPagedData { it.toDomain() }
    }
}
