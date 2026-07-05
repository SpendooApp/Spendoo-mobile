package org.spendoo.scraper.data.remote

import org.spendoo.scraper.domain.entity.ScrapeResponse

object AmazonDataCleaner {
    fun cleanData(socialDetails: List<ScrapeResponse>): Pair<List<ScrapeResponse>, List<ScrapeResponse>> {
        val success = mutableListOf<ScrapeResponse>()
        val unknownError = mutableListOf<ScrapeResponse>()

        for (detail in socialDetails) {
            if (detail.error == null) {
                success.add(detail)
            } else if (detail.error == AmazonConstants.FAILED_DUE_TO_UNKNOWN_ERROR) {
                unknownError.add(detail)
            }
        }
        return Pair(success, unknownError)
    }

    fun printDataErrors(unknownError: List<ScrapeResponse>) {
        if (unknownError.isNotEmpty()) {
            val name = if (unknownError.size > 1) "queries" else "query"
            println("Could not get data for ${unknownError.size} $name due to an unexpected scraping error.")
        }
    }
}
