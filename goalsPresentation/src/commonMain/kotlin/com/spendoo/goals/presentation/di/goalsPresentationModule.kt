package com.spendoo.goals.presentation.di

import com.spendoo.goals.presentation.screen.goals.GoalsViewModel
import com.spendoo.goals.presentation.screen.addEditGoal.AddGoalViewModel
import com.spendoo.goals.presentation.screen.achievements.AchievementsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val goalsPresentationModule = module {
    viewModelOf(::GoalsViewModel)
    viewModelOf(::AddGoalViewModel)
    viewModelOf(::AchievementsViewModel)
}
