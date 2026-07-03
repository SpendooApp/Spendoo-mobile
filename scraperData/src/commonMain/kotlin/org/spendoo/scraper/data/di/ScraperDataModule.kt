package org.spendoo.scraper.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.spendoo.scraper.data.remote.AmazonScraper
import org.spendoo.scraper.data.remote.KmpHtmlFetcher
import org.spendoo.scraper.data.remote.KtorAmazonFetcher
import org.spendoo.scraper.data.repository.AmazonScraperRepositoryImpl
import org.spendoo.scraper.domain.repository.AmazonScraperRepository

val scraperDataModule = module {
    single<KmpHtmlFetcher> { KtorAmazonFetcher() }
    single { AmazonScraper(get()) }
    singleOf(::AmazonScraperRepositoryImpl) bind AmazonScraperRepository::class
}
