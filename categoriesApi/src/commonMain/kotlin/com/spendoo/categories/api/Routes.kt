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
