package com.spendoo.di

import com.spendoo.categories.data.di.categoriesDataModule
import com.spendoo.categories.presentation.di.categoriesPresentationModule
import com.spendoo.goals.data.di.goalsDataModule
import com.spendoo.goals.presentation.di.goalsPresentationModule
import com.spendoo.statistics.presentation.di.statisticsPresentationModule
import com.spendoo.statistics.data.di.statisticsDataModule
import com.spendoo.offers.data.di.offersDataModule
import com.spendoo.home.presentation.di.homePresentationModule
import com.spendoo.identity.domain.di.domainModule as identityDomainModule
import com.spendoo.identity.presentation.di.identityScreensModule
import com.spendoo.identity.data.di.identityDataModule
import com.spendoo.chatbot.presentation.di.chatbotPresentationModule
import com.spendoo.logging.di.loggingModule
import org.koin.dsl.module

val featureModule = module {
    includes(
        identityScreensModule,
        identityDomainModule,
        identityDataModule,
        categoriesDataModule,
        categoriesPresentationModule,
        offersDataModule,
        homePresentationModule,
        goalsDataModule,
        goalsPresentationModule,
        statisticsPresentationModule,
        statisticsDataModule,
        chatbotPresentationModule,
        loggingModule
    )
}