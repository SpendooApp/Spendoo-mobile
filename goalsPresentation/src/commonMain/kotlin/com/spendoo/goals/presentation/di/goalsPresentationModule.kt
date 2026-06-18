package com.spendoo.goals.presentation.di

import com.spendoo.goals.presentation.screen.goals.GoalsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val goalsPresentationModule = module {
    viewModelOf(::GoalsViewModel)
}
