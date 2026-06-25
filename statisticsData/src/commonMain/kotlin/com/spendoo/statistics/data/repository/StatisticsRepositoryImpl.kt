package com.spendoo.statistics.data.repository

import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.statistics.data.dataSource.remote.dto.CombinedStatsResponse
import com.spendoo.statistics.data.dataSource.remote.dto.toDomain
import com.spendoo.statistics.data.dataSource.remote.endpoint.StatisticsEndpoints
import com.spendoo.statistics.domain.entity.CombinedStats
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.repository.StatisticsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.datetime.LocalDateTime

class StatisticsRepositoryImpl(
    client: HttpClient,
) : BaseGateway(client), StatisticsRepository {

    override suspend fun getStatistics(
        granularity: Granularity,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): CombinedStats {
        val response = tryToExecute<CombinedStatsResponse> {
            get(StatisticsEndpoints.STATISTICS) {
                url {
                    parameters.append("granularity", granularity.name)
                    parameters.append("start_date", startDate.toString())
                    parameters.append("end_date", endDate.toString())
                }
            }
        }
        return response.toDomain()
    }
}


