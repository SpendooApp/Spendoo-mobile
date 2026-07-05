package com.spendoo.di

import com.spendoo.categories.data.di.categoriesDataModule
import com.spendoo.categories.presentation.di.categoriesPresentationModule
import com.spendoo.chatbot.data.di.chatbotDataModule
import com.spendoo.chatbot.presentation.di.chatbotPresentationModule
import com.spendoo.goals.data.di.goalsDataModule
import com.spendoo.goals.presentation.di.goalsPresentationModule
import com.spendoo.home.presentation.di.homePresentationModule
import com.spendoo.identity.data.di.identityDataModule
import com.spendoo.identity.presentation.di.identityScreensModule
import com.spendoo.logging.di.loggingModule
import com.spendoo.notifications.data.di.notificationsDataModule
import com.spendoo.offers.data.di.offersDataModule
import com.spendoo.statistics.data.di.statisticsDataModule
import com.spendoo.statistics.presentation.di.statisticsPresentationModule
import org.koin.dsl.module
import org.spendoo.scraper.data.di.scraperDataModule
import com.spendoo.identity.domain.di.domainModule as identityDomainModule

val featureModule = module {
    includes(
        identityScreensModule,
        identityDomainModule,
        identityDataModule,
        categoriesDataModule,
        categoriesPresentationModule,
        offersDataModule,
        scraperDataModule,
        homePresentationModule,
        goalsDataModule,
        goalsPresentationModule,
        statisticsPresentationModule,
        statisticsDataModule,
        chatbotDataModule,
        chatbotPresentationModule,
        notificationsDataModule,
        loggingModule
    )
}