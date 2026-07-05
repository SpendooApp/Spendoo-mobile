package com.spendoo.goals.data.di

import com.spendoo.goals.data.repository.AchievementRepositoryImpl
import com.spendoo.goals.data.repository.GoalsRepositoryImpl
import com.spendoo.goals.domain.repository.AchievementRepository
import com.spendoo.goals.domain.repository.GoalsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val goalsDataModule = module {
    singleOf(::GoalsRepositoryImpl) bind GoalsRepository::class
    singleOf(::AchievementRepositoryImpl) bind AchievementRepository::class
}

