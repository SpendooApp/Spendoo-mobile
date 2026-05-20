package com.spendoo.goals.data.di

import com.spendoo.goals.data.repository.GoalsRepositoryImpl
import com.spendoo.goals.domain.repository.GoalsRepository
import org.koin.dsl.module

val goalsDataModule = module {
    single<GoalsRepository> { GoalsRepositoryImpl() }
}
