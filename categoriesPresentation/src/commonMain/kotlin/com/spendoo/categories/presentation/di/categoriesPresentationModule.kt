package com.spendoo.categories.presentation.di

import com.spendoo.categories.presentation.navigation.effector.Effector
import com.spendoo.categories.presentation.navigation.effector.EffectorImpl
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.AddCategoryViewModel
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionViewModel
import com.spendoo.categories.presentation.screen.inputVoiceBottomSheet.InputVoiceViewModel
import com.spendoo.categories.presentation.screen.categorySelectionSheet.CategorySelectionViewModel
import com.spendoo.categories.presentation.screen.categories.CategoriesViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val categoriesPresentationModule = module {
    singleOf(::EffectorImpl) bind Effector::class
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::AddTransactionViewModel)
    viewModelOf(::AddCategoryViewModel)
    viewModelOf(::CategorySelectionViewModel)
    viewModelOf(::InputVoiceViewModel)
}
