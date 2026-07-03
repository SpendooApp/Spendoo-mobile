package org.spendoo.scraper.data.repository

import org.spendoo.scraper.data.remote.AmazonDataCleaner
import org.spendoo.scraper.data.remote.AmazonScraper
import org.spendoo.scraper.domain.entity.AmazonProduct
import org.spendoo.scraper.domain.entity.ScrapeRequest
import org.spendoo.scraper.domain.repository.AmazonScraperRepository

class AmazonScraperRepositoryImpl(
    private val scraper: AmazonScraper = AmazonScraper()
) : AmazonScraperRepository {

    override suspend fun search(
        query: List<String>,
        key: String?,
        country: String,
        language: String,
        sortBy: String?,
        offersOnly: Boolean,
        minPrice: Double?,
        maxPrice: Double?
    ): List<AmazonProduct> {
        val resultList = mutableListOf<AmazonProduct>()

        // Construct requests for each element in string collection safely
        val queryItems = query.map { singleQuery ->
            ScrapeRequest(
                query = singleQuery,
                country = country,
                language = language,
                sortBy = sortBy,
                offersOnly = offersOnly,
                minPrice = minPrice,
                maxPrice = maxPrice,
                maxItems = null
            )
        }

        for (requestItem in queryItems) {
            // Execution call matching the Python decorator runtime pipeline
            val resultItem = scraper.searchHtml(requestItem)

            val (success, unknownError) = AmazonDataCleaner.cleanData(listOf(resultItem))
            AmazonDataCleaner.printDataErrors(unknownError)

            if (success.isNotEmpty()) {
                val scrapeData = resultItem.data
                val productResults = scrapeData?.results ?: emptyList()
                resultList.addAll(productResults)
            }
        }

        return resultList
    }

    override suspend fun search(
        query: String,
        key: String?,
        country: String,
        language: String,
        sortBy: String?,
        offersOnly: Boolean,
        minPrice: Double?,
        maxPrice: Double?
    ): List<AmazonProduct> = search(
        listOf(query),
        key,
        country,
        language,
        sortBy,
        offersOnly,
        minPrice,
        maxPrice
    )
}
