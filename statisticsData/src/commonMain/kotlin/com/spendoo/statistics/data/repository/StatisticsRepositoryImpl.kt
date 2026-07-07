package com.spendoo.statistics.data.repository

import com.spendoo.identity.domain.util.AppTheme
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.shared.domain.utils.toUtcInstant
import com.spendoo.statistics.data.dataSource.remote.dto.CombinedStatsResponse
import com.spendoo.statistics.data.dataSource.remote.dto.toDomain
import com.spendoo.statistics.data.dataSource.remote.endpoint.StatisticsEndpoints
import com.spendoo.statistics.domain.entity.CombinedStats
import com.spendoo.statistics.domain.entity.ExportChoices
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.entity.ReportDataType
import com.spendoo.statistics.domain.repository.StatisticsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import kotlinx.datetime.LocalDateTime

class StatisticsRepositoryImpl(
    client: HttpClient,
) : BaseGateway(client), StatisticsRepository {

    private var cachedExportChoices: ExportChoices? = null

    override fun saveExportChoices(choices: ExportChoices) {
        cachedExportChoices = choices
    }

    override fun getExportChoices(): ExportChoices? {
        return cachedExportChoices
    }

    override fun clearExportChoices() {
        cachedExportChoices = null
    }

    override suspend fun getStatistics(
        granularity: Granularity,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): CombinedStats {
        val response = tryToExecute<CombinedStatsResponse> {
            get(StatisticsEndpoints.STATISTICS) {
                url {
                    parameters.append("granularity", granularity.name)
                    parameters.append("start_date", startDate.toUtcInstant().toString())
                    parameters.append("end_date", endDate.toUtcInstant().toString())
                }
            }
        }
        return response.toDomain()
    }

    override suspend fun getStatisticsPdf(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        reportDataType: ReportDataType,
        theme: AppTheme
    ): ByteArray {
        val response = tryToExecute<ByteArray> {
            get("/api/v1/statistics/pdf") {
                timeout { //TODO: Check the enough timeout for large PDFs
                    requestTimeoutMillis = Long.MAX_VALUE
                    socketTimeoutMillis = Long.MAX_VALUE
                }
                url {
                    parameters.append("start_date", startDate.toUtcInstant().toString())
                    parameters.append("end_date", endDate.toUtcInstant().toString())
                    parameters.append("reportDataType", reportDataType.name)
                }
            }
        }
        return response
    }

    override suspend fun getUserStatistics(
        targetUserId: String,
        granularity: Granularity,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): CombinedStats {
        val response = tryToExecute<CombinedStatsResponse> {
            get("/api/v1/statistics/user/$targetUserId") {
                url {
                    parameters.append("granularity", granularity.name)
                    parameters.append("start_date", startDate.toUtcInstant().toString())
                    parameters.append("end_date", endDate.toUtcInstant().toString())
                }
            }
        }
        return response.toDomain()
    }

    override suspend fun getUserStatisticsPdf(
        targetUserId: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        reportDataType: ReportDataType,
        theme: AppTheme
    ): ByteArray {
        val response = tryToExecute<ByteArray> {
            get("/api/v1/statistics/user/$targetUserId/pdf") {
                url {
                    parameters.append("start_date", startDate.toUtcInstant().toString())
                    parameters.append("end_date", endDate.toUtcInstant().toString())
                    parameters.append("reportDataType", reportDataType.name)
                }
            }
        }
        return response
    }
}
