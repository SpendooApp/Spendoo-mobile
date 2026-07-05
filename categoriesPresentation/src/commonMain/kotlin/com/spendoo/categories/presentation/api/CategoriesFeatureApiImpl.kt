package com.spendoo.categories.presentation.api

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import com.spendoo.categories.api.AddTransactionRoute
import com.spendoo.categories.api.CategoriesFeatureApi
import com.spendoo.categories.api.CategoriesRoute
import com.spendoo.categories.api.FinancialActionRoute
import com.spendoo.categories.api.ScheduledPaymentDetailsRoute
import com.spendoo.categories.api.ScheduledPaymentsRoute
import com.spendoo.categories.api.TransactionDetailsRoute
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionBottomSheet
import com.spendoo.categories.presentation.screen.categories.CategoriesScreen
import com.spendoo.categories.presentation.screen.financialActionScreen.FinancialActionScreen
import com.spendoo.categories.presentation.screen.scheduledPaymentDetails.ScheduledPaymentDetailsScreen
import com.spendoo.categories.presentation.screen.scheduledPayments.ScheduledPaymentsScreen
import com.spendoo.categories.presentation.screen.transactionDetails.TransactionDetailsScreen
import com.spendoo.categories.presentation.screen.topSpendingCategories.TopSpendingCategoriesScreen
import com.spendoo.categories.presentation.screen.categoryOffers.CategoryOffersScreen
import com.spendoo.categories.presentation.screen.editTransactionBottomSheet.EditTransactionBottomSheet
import com.spendoo.categories.api.TopSpendingCategoriesRoute
import com.spendoo.categories.api.CategoryOffersRoute
import com.spendoo.categories.api.EditTransactionRoute
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
            entry< FinancialActionRoute>(
                metadata = DialogSceneStrategy.dialog()
            ) { route ->
                FinancialActionScreen(
                    tile = route.tile,
                    body = route.body,
                    payload = route.payload
                )
            }
            entry<TopSpendingCategoriesRoute> { TopSpendingCategoriesScreen() }
            entry<CategoryOffersRoute> { route ->
                CategoryOffersScreen(categoryId = route.categoryId)
            }
            entry<EditTransactionRoute>(
                metadata = DialogSceneStrategy.dialog()
            ) { route ->
                EditTransactionBottomSheet(transactionId = route.transactionId)
            }
        }
    }
}