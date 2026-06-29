package com.spendoo.statistics.data.repository

import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.util.AppTheme
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.statistics.data.dataSource.remote.dto.CombinedStatsResponse
import com.spendoo.statistics.data.dataSource.remote.dto.toDomain
import com.spendoo.statistics.data.dataSource.remote.endpoint.StatisticsEndpoints
import com.spendoo.statistics.domain.entity.CombinedStats
import com.spendoo.statistics.domain.entity.ExportChoices
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.entity.ReportDataType
import com.spendoo.statistics.domain.repository.StatisticsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import kotlinx.datetime.LocalDateTime

class StatisticsRepositoryImpl(
    client: HttpClient,
    private val settingsRepository: SettingsRepository
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
                    parameters.append("start_date", startDate.toString())
                    parameters.append("end_date", endDate.toString())
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
        val lang = settingsRepository.getCurrentAppLanguage()

        val response = tryToExecute<ByteArray> {
            get("/api/v1/statistics/pdf") {
                url {
                    parameters.append("start_date", startDate.toString())
                    parameters.append("end_date", endDate.toString())
                    parameters.append("reportDataType", reportDataType.name)
                }
                headers {
                    set("X-App-Theme", theme.name)
                    set("Accept-Language", lang.name)
                }
            }
        }
        return response
    }
}


