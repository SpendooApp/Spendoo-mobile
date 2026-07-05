package com.spendoo.identity.domain.repository

import com.spendoo.identity.domain.model.BillingCycle
import com.spendoo.identity.domain.model.SubscriptionPlan

interface SubscriptionRepository {
    suspend fun getSubscriptionPlans(languageCode: String = "en"): List<SubscriptionPlan>
    suspend fun subscribePlan(planId: String, billingCycle: BillingCycle?)
}
