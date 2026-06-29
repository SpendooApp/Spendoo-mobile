package com.spendoo.categories.presentation.di

import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.AddCategoryViewModel
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionViewModel
import com.spendoo.categories.presentation.screen.inputVoiceBottomSheet.InputVoiceViewModel
import com.spendoo.categories.presentation.screen.categorySelectionSheet.CategorySelectionViewModel
import com.spendoo.categories.presentation.screen.categories.CategoriesViewModel
import com.spendoo.categories.presentation.screen.scheduledPaymentDetails.ScheduledPaymentDetailsViewModel
import com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet.AddScheduledPaymentViewModel
import com.spendoo.categories.presentation.screen.scheduledPayments.ScheduledPaymentsViewModel
import com.spendoo.categories.presentation.screen.transactionDetails.TransactionDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val categoriesPresentationModule = module {
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::AddTransactionViewModel)
    viewModelOf(::AddCategoryViewModel)
    viewModelOf(::CategorySelectionViewModel)
    viewModelOf(::InputVoiceViewModel)
    viewModelOf(::ScheduledPaymentDetailsViewModel)
    viewModelOf(::AddScheduledPaymentViewModel)
    viewModelOf(::ScheduledPaymentsViewModel)
    viewModelOf(::TransactionDetailsViewModel)
}
