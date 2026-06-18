package com.spendoo.categories.data.repository

import com.spendoo.categories.data.dataSource.remote.dto.BasePagedData
import com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment.CreateScheduledPaymentDto
import com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment.ScheduledPaymentDto
import com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment.ScheduledPaymentsSummaryDto
import com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment.toDomain
import com.spendoo.categories.data.dataSource.remote.dto.scheduledPayment.toDto
import com.spendoo.categories.data.dataSource.remote.dto.toPagedData
import com.spendoo.categories.data.dataSource.remote.endpoint.ScheduledPaymentsEndpoints
import com.spendoo.categories.data.shared.BaseGateway
import com.spendoo.categories.domain.entity.scheduledPayment.CreateScheduledPayment
import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPayment
import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPaymentsSummary
import com.spendoo.categories.domain.repository.ScheduledPaymentsRepository
import com.spendoo.categories.domain.utils.PageQuery
import com.spendoo.categories.domain.utils.PagedData
import com.spendoo.categories.domain.utils.orEmpty
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments

class ScheduledPaymentsRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), ScheduledPaymentsRepository {

    override suspend fun getScheduledPayments(pageQuery: PageQuery): PagedData<ScheduledPayment> {
        val response = tryToExecute<BasePagedData<ScheduledPaymentDto>> {
            get(ScheduledPaymentsEndpoints.SCHEDULED_PAYMENTS) {
                url {
                    parameters.append("page", pageQuery.page.toString())
                    parameters.append("size", pageQuery.size.toString())
                    pageQuery.sort?.forEach { sort -> parameters.append("sort", sort) }
                }
            }
        }
        return response.toPagedData { it.toDomain() }.orEmpty()
    }

    override suspend fun getScheduledPaymentsSummary(): ScheduledPaymentsSummary {
        return tryToExecute<ScheduledPaymentsSummaryDto> {
            get(ScheduledPaymentsEndpoints.SUMMARY)
        }.toDomain()
    }

    override suspend fun getScheduledPayment(id: String): ScheduledPayment {
        val response = tryToExecute<ScheduledPaymentDto> {
            get(ScheduledPaymentsEndpoints.SCHEDULED_PAYMENTS) {
                url { appendPathSegments(id) }
            }
        }
        return response.toDomain()
    }

    override suspend fun addScheduledPayment(payment: CreateScheduledPayment) {
        tryToExecute<Unit> {
            post(ScheduledPaymentsEndpoints.SCHEDULED_PAYMENTS) {
                setBody(payment.toDto())
            }
        }
    }

    override suspend fun updateScheduledPayment(id: String, payment: CreateScheduledPayment) {
        tryToExecute<Unit> {
            patch(ScheduledPaymentsEndpoints.SCHEDULED_PAYMENTS) {
                url { appendPathSegments(id) }
                setBody(payment.toDto())
            }
        }
    }

    override suspend fun deleteScheduledPayment(id: String) {
        tryToExecute<Unit> {
            delete(ScheduledPaymentsEndpoints.SCHEDULED_PAYMENTS) {
                url { appendPathSegments(id) }
            }
        }
    }

    override suspend fun skipScheduledPayment(id: String) {
        tryToExecute<Unit> {
            post(ScheduledPaymentsEndpoints.SCHEDULED_PAYMENTS) {
                url {
                    appendPathSegments(id)
                    appendPathSegments("skip")
                }
            }
        }
    }

    override suspend fun payScheduledPayment(id: String) {
        tryToExecute<Unit> {
            post(ScheduledPaymentsEndpoints.SCHEDULED_PAYMENTS) {
                url {
                    appendPathSegments(id)
                    appendPathSegments("pay")
                }
            }
        }
    }
}
