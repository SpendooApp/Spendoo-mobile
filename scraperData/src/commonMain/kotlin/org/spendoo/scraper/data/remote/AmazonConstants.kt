package org.spendoo.scraper.data.remote

object AmazonConstants {
    const val FAILED_DUE_TO_UNKNOWN_ERROR = "FAILED_DUE_TO_UNKNOWN_ERROR"

    val USER_AGENTS = listOf(
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/120.0",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.1 Safari/605.1.15",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36"
    )

    val AMAZON_DOMAINS = mapOf(
        "com" to "www.amazon.com", "de" to "www.amazon.de", "co.uk" to "www.amazon.co.uk",
        "fr" to "www.amazon.fr", "es" to "www.amazon.es", "it" to "www.amazon.it",
        "co.jp" to "www.amazon.co.jp", "ca" to "www.amazon.ca", "com.au" to "www.amazon.com.au",
        "in" to "www.amazon.in", "com.br" to "www.amazon.com.br", "com.mx" to "www.amazon.com.mx",
        "ae" to "www.amazon.ae", "cn" to "www.amazon.cn", "eg" to "www.amazon.eg"
    )

    val DEFAULT_CURRENCIES = mapOf(
        "com" to "$", "de" to "€", "co.uk" to "£", "fr" to "€", "es" to "€", "it" to "€",
        "co.jp" to "¥", "ca" to "CAD", "com.au" to "AUD", "in" to "INR", "com.br" to "BRL",
        "com.mx" to "MXN", "ae" to "AED", "cn" to "CNY", "eg" to "ج.م."
    )

    val SORT_OPTIONS = mapOf(
        "relevance" to "relevancerank",
        "price_asc" to "price-asc-rank",
        "price_desc" to "price-desc-rank",
        "reviews" to "review-rank",
        "newest" to "date-desc-rank"
    )

    val DEFAULT_HEADERS = mapOf(
        "Accept-Language" to "en-US,en;q=0.9",
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Connection" to "keep-alive"
    )
}
