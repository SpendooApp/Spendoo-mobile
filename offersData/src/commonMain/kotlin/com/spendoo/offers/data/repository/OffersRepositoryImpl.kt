package com.spendoo.offers.data.repository

import com.spendoo.identity.domain.util.AppLocalizer
import com.spendoo.offers.data.local.dao.OffersDao
import com.spendoo.offers.data.local.entity.KeywordCacheEntity
import com.spendoo.offers.data.local.entity.OfferEntity
import com.spendoo.offers.domain.entity.Offer
import com.spendoo.offers.domain.repository.OffersRepository
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.spendoo.scraper.domain.repository.AmazonScraperRepository
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

class OffersRepositoryImpl(
    private val offersDao: OffersDao,
    private val scraperRepository: AmazonScraperRepository,
    private val appLocalizer: AppLocalizer
) : OffersRepository {

    override suspend fun getOffers(
        query: PageQuery,
        keywords: List<String>,
        maxPrice: Double?
    ): PagedData<Offer> {
        if (keywords.isEmpty()) {
            return PagedData(emptyList(), 0, true)
        }

        val language = appLocalizer.getDeviceLanguageIso()
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val fourteenDaysInMillis = 14.days.inWholeMilliseconds

        val cacheStatuses = offersDao.getCacheStatusForKeywords(keywords, language)
        val missingOrExpiredKeywords = keywords.filter { kw ->
            val status = cacheStatuses.find { it.keyword == kw }
            status == null || (currentTime - status.lastFetchTime) > fourteenDaysInMillis
        }

        if (missingOrExpiredKeywords.isNotEmpty()) {
            // Delete old data for expired keywords
            offersDao.deleteOffersForKeywords(missingOrExpiredKeywords, language)

            // Fetch new data per keyword concurrently to map them correctly in DB
            coroutineScope {
                val results = missingOrExpiredKeywords.map { kw ->
                    async {
                        val newOffers = scraperRepository.search(
                            query = listOf(kw),
                            sortBy = "price_asc",
                            maxPrice = maxPrice,
                            offersOnly = true,
                            country = "eg",
                            language = language
                        )

                        val entities = newOffers.mapIndexed { index, product ->
                            OfferEntity(
                                id = product.link ?: "${product.title.hashCode()}_$index",
                                keyword = kw,
                                language = language,
                                discountPercent = product.discountPercent,
                                imageUrl = product.imageUrl,
                                title = product.title,
                                price = product.price,
                                currency = product.currency,
                                rating = product.rating,
                                link = product.link
                            )
                        }
                        kw to entities
                    }
                }.awaitAll()

                results.forEach { (kw, entities) ->
                    offersDao.insertOffers(entities)
                    offersDao.insertKeywordCacheStatus(listOf(KeywordCacheEntity(kw, language, currentTime)))
                }
            }
        }

        val offset = query.page * query.size
        val totalCount = offersDao.getOffersCountForKeywords(keywords, language)
        val cachedOffers = offersDao.getOffersForKeywords(
            keywords = keywords,
            language = language,
            limit = query.size,
            offset = offset
        )
        val mappedOffers = cachedOffers.map { entity ->
            Offer(
                id = entity.id,
                discountPercent = entity.discountPercent,
                imageUrl = entity.imageUrl,
                title = entity.title,
                price = entity.price,
                currency = entity.currency,
                rating = entity.rating,
                link = entity.link
            )
        }

        return PagedData(
            data = mappedOffers,
            totalItems = totalCount,
            isLastPage = (offset + mappedOffers.size) >= totalCount
        )


    }
}
