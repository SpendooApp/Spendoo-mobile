package com.spendoo.identity.data.repository

import com.spendoo.identity.data.dataSource.remote.dto.subscription.request.SubscribePlanRequestDto
import com.spendoo.identity.data.dataSource.remote.dto.subscription.response.SubscriptionPlanDto
import com.spendoo.identity.data.dataSource.remote.dto.subscription.response.toDomain
import com.spendoo.identity.domain.model.BillingCycle
import com.spendoo.identity.domain.model.SubscriptionPlan
import com.spendoo.identity.domain.repository.SubscriptionRepository
import com.spendoo.shared.data.shared.BaseGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class SubscriptionRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), SubscriptionRepository {

    override suspend fun getSubscriptionPlans(languageCode: String): List<SubscriptionPlan> {
        val response = tryToExecute<List<SubscriptionPlanDto>> {
            get("api/v1/subscriptions/plans")
        }
        return response.map { it.toDomain() }
    }

    override suspend fun subscribePlan(planId: String, billingCycle: BillingCycle?) {
        tryToExecute<Unit> {
            post("api/v1/subscriptions/subscribe") {
                setBody(
                    SubscribePlanRequestDto(
                        planId = planId,
                        billingCycle = billingCycle
                    )
                )
            }
        }
    }
}
