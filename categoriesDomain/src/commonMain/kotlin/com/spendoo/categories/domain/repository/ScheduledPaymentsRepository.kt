package com.spendoo.categories.domain.repository

import com.spendoo.categories.domain.entity.scheduledPayment.CreateScheduledPayment
import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPayment
import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPaymentsSummary
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData

interface ScheduledPaymentsRepository {
    suspend fun getScheduledPayments(pageQuery: PageQuery): PagedData<ScheduledPayment>
    suspend fun getScheduledPaymentsSummary(): ScheduledPaymentsSummary
    suspend fun getScheduledPayment(id: String): ScheduledPayment
    suspend fun addScheduledPayment(payment: CreateScheduledPayment)
    suspend fun updateScheduledPayment(id: String, payment: CreateScheduledPayment)
    suspend fun deleteScheduledPayment(id: String)
    suspend fun skipScheduledPayment(id: String)
    suspend fun payScheduledPayment(id: String)
}
