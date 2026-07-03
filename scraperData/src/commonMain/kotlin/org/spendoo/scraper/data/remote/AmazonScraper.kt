package org.spendoo.scraper.data.remote

import com.fleeksoft.ksoup.Ksoup
import org.spendoo.scraper.domain.entity.AmazonProduct
import org.spendoo.scraper.domain.entity.ScrapeData
import org.spendoo.scraper.domain.entity.ScrapeRequest
import org.spendoo.scraper.domain.entity.ScrapeResponse

class AmazonScraper(private val fetcher: KmpHtmlFetcher = KtorAmazonFetcher()) {

    suspend fun searchHtml(request: ScrapeRequest): ScrapeResponse {
        val baseDomain = AmazonConstants.AMAZON_DOMAINS[request.country] ?: AmazonConstants.AMAZON_DOMAINS["com"]!!
        val url = "https://$baseDomain/s"

        val params = mutableMapOf("k" to request.query)
        
        request.sortBy?.let {
            if (it in AmazonConstants.SORT_OPTIONS) {
                params["s"] = AmazonConstants.SORT_OPTIONS[it]!!
            }
        }

        if (request.offersOnly) {
            params["f"] = "p_n_deal_type%3A2356606011"
        }

        if (request.minPrice != null || request.maxPrice != null) {
            var priceRangeStr = ""
            request.minPrice?.let { priceRangeStr += (it * 100).toInt().toString() }
            priceRangeStr += "-"
            request.maxPrice?.let { priceRangeStr += (it * 100).toInt().toString() }
            if (priceRangeStr != "-") {
                params["rh"] = "p_36:$priceRangeStr"
            }
        }

        val headers = mapOf("Accept-Language" to request.language)

        return try {
            val html = fetcher.fetchHtml(url, params = params, headers = headers)
            val doc = Ksoup.parse(html)
            val results = mutableListOf<AmazonProduct>()

            val searchCards = doc.select("div[data-component-type=\"s-search-result\"]")
            
            for (card in searchCards) {
                if (card.attr("data-asin").isBlank()) continue

                // --- Title Extraction with Fallbacks ---
                val fullTitleEl = card.selectFirst("h2.a-size-medium.a-spacing-none.a-color-base.a-text-normal")
                var title = if (fullTitleEl != null && fullTitleEl.hasAttr("aria-label")) {
                    KmpAmazonParserUtils.cleanText(fullTitleEl.attr("aria-label"))
                } else null

                if (title.isNullOrBlank()) {
                    val titleEl = card.selectFirst("h2 a span") 
                        ?: card.selectFirst("h2 span.a-size-base-plus") 
                        ?: card.selectFirst("h2 span")
                    title = titleEl?.text()?.let { KmpAmazonParserUtils.cleanText(it) }
                }

                // --- Link Extraction with Fallbacks ---
                var linkEl = card.selectFirst("h2 a.a-link-normal.s-underline-text.s-underline-link-text")
                if (linkEl == null) linkEl = card.selectFirst("h2 a[href]")
                if (linkEl == null) linkEl = card.selectFirst("a.a-link-normal.s-link-style[href]")

                var link: String? = null
                if (linkEl != null) {
                    val hrefValue = linkEl.attr("href")
                    if (hrefValue.isNotBlank() && hrefValue != "#") {
                        link = KmpAmazonParserUtils.buildAbsoluteUrl(hrefValue, baseDomain)
                    }
                }

                // --- Image Element Extraction ---
                val imageEl = card.selectFirst("img.s-image")
                val imageUrl = imageEl?.attr("src")

                // --- Price & Currency Extraction with Fallbacks ---
                var price: Double? = null
                var currency: String? = null
                val priceOffscreenEl = card.selectFirst("span.a-price > span.a-offscreen")
                val secondaryOfferPriceEl = card.selectFirst("div[data-cy='secondary-offer-recipe'] span.a-color-base")

                if (priceOffscreenEl != null) {
                    val parsed = KmpAmazonParserUtils.parsePrice(priceOffscreenEl.text())
                    price = parsed.first
                    currency = parsed.second
                } else if (secondaryOfferPriceEl != null) {
                    val parsed = KmpAmazonParserUtils.parsePrice(secondaryOfferPriceEl.text())
                    price = parsed.first
                    currency = parsed.second
                } else {
                    val priceWholeEl = card.selectFirst("span.a-price-whole")
                    val priceFractionEl = card.selectFirst("span.a-price-fraction")
                    if (priceWholeEl != null) {
                        val fractionText = if (priceFractionEl != null) ".${priceFractionEl.text()}" else ""
                        val parsed = KmpAmazonParserUtils.parsePrice(priceWholeEl.text() + fractionText)
                        price = parsed.first
                        currency = parsed.second
                    }
                }

                // Fill Missing Currencies matching Python country detection hooks
                if (price != null && currency == null && request.country in AmazonConstants.DEFAULT_CURRENCIES) {
                    currency = AmazonConstants.DEFAULT_CURRENCIES[request.country]
                }

                // --- Rating Extraction ---
                val ratingEl = card.selectFirst("i.a-icon-star span.a-icon-alt") ?: card.selectFirst("span.a-icon-alt")
                val rating = KmpAmazonParserUtils.parseRating(ratingEl?.text())

                // --- Reviews Extraction with Fallbacks ---
                val reviewsElLink = card.selectFirst("a.a-link-normal.s-underline-text.s-underline-link-text")
                var reviews: Int? = null
                if (reviewsElLink != null && reviewsElLink.hasAttr("aria-label")) {
                    reviews = KmpAmazonParserUtils.parseReviews(reviewsElLink.attr("aria-label"))
                } else {
                    val plainReviewTextEl = card.selectFirst("span.a-size-base.s-underline-text")
                    if (plainReviewTextEl != null) {
                        reviews = KmpAmazonParserUtils.parseReviews(plainReviewTextEl.text())
                    } else {
                        val endingWithRatingsEl = card.selectFirst("span[aria-label$= ratings]")
                        if (endingWithRatingsEl != null) {
                            reviews = KmpAmazonParserUtils.parseReviews(endingWithRatingsEl.attr("aria-label"))
                        }
                    }
                }

                // --- Description Extraction with Fallbacks ---
                var description = fullTitleEl?.attr("aria-label")?.let { KmpAmazonParserUtils.cleanText(it) }
                if (description.isNullOrBlank()) {
                    val descriptionEl = card.selectFirst("div.a-section.a-spacing-none.a-spacing-top-small span.a-size-base-plus")
                        ?: card.selectFirst("div.a-section.a-spacing-none.a-spacing-top-mini span.a-size-base-plus")
                        ?: card.selectFirst("span.a-text-normal.a-color-base")
                    description = descriptionEl?.text()?.let { KmpAmazonParserUtils.cleanText(it) }
                }

                // Guard constraint check
                if (title.isNullOrBlank() || link.isNullOrBlank()) {
                    continue
                }

                results.add(
                    AmazonProduct(
                        title = title,
                        description = description,
                        price = price,
                        currency = currency,
                        rating = rating,
                        reviews = reviews,
                        link = link,
                        imageUrl = imageUrl
                    )
                )
                val maxItems = request.maxItems
                if (maxItems != null && results.size >= maxItems) {
                    break
                }
            }

            ScrapeResponse(data = ScrapeData(count = results.size, results = results), error = null)
        } catch (e: Exception) {
            println("Search scraping failed: ${e.message}")
            ScrapeResponse(data = null, error = AmazonConstants.FAILED_DUE_TO_UNKNOWN_ERROR)
        }
    }
}
