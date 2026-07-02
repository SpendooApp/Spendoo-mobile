package com.spendoo.categories.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object CategoriesRoute : NavKey

@Serializable
data object AddTransactionRoute : NavKey

@Serializable
data object ScheduledPaymentsRoute : NavKey

@Serializable
data class ScheduledPaymentDetailsRoute(val paymentId: String) : NavKey

@Serializable
data class TransactionDetailsRoute(val transactionId: String) : NavKey

@Serializable
data class FinancialActionRoute(val tile: String, val body: String, val payload: Map<String, String>) : NavKey