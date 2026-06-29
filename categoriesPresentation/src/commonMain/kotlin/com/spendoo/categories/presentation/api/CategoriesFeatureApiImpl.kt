package com.spendoo.categories.presentation.api

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import com.spendoo.categories.api.AddTransactionRoute
import com.spendoo.categories.api.CategoriesFeatureApi
import com.spendoo.categories.api.CategoriesRoute
import com.spendoo.categories.api.ScheduledPaymentDetailsRoute
import com.spendoo.categories.api.ScheduledPaymentsRoute
import com.spendoo.categories.api.TransactionDetailsRoute
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionBottomSheet
import com.spendoo.categories.presentation.screen.categories.CategoriesScreen
import com.spendoo.categories.presentation.screen.scheduledPaymentDetails.ScheduledPaymentDetailsScreen
import com.spendoo.categories.presentation.screen.scheduledPayments.ScheduledPaymentsScreen
import com.spendoo.categories.presentation.screen.transactionDetails.TransactionDetailsScreen


class CategoriesFeatureApiImpl : CategoriesFeatureApi {

    override fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<CategoriesRoute> { CategoriesScreen() }
            entry<AddTransactionRoute>(
                metadata = DialogSceneStrategy.dialog()
            ) { AddTransactionBottomSheet() }
            entry<ScheduledPaymentsRoute> { ScheduledPaymentsScreen() }
            entry<ScheduledPaymentDetailsRoute> { route ->
                ScheduledPaymentDetailsScreen(paymentId = route.paymentId)
            }
            entry<TransactionDetailsRoute> { route ->
                TransactionDetailsScreen(transactionId = route.transactionId)
            }
        }
    }
}