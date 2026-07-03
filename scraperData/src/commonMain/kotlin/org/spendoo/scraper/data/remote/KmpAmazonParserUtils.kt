package org.spendoo.scraper.data.remote

object KmpAmazonParserUtils {

    fun parsePrice(text: String?): Pair<Double?, String?> {
        if (text.isNullOrBlank()) return Pair(null, null)
        val currencyRegex = Regex("""([A-Z]{2,3}|\$|£|€|ج\.م\.)""")
        val amountRegex = Regex("""([0-9][0-9,]*(?:\.[0-9]{1,2})?)""")

        val currency = currencyRegex.find(text)?.groupValues?.get(1)
        val amountStr = amountRegex.find(text)?.groupValues?.get(1)
        val amount = amountStr?.replace(",", "")?.toDoubleOrNull()

        return Pair(amount, currency)
    }

    fun parsePriceParts(wholeText: String?, fractionText: String?): Double? {
        if (wholeText.isNullOrBlank()) return null
        val whole = wholeText.replace(Regex("[^0-9]"), "")
        val fraction = fractionText?.replace(Regex("[^0-9]"), "") ?: ""
        if (whole.isBlank()) return null
        return if (fraction.isNotBlank()) "$whole.$fraction".toDoubleOrNull() else whole.toDoubleOrNull()
    }

    fun parseRating(text: String?): Double? {
        if (text.isNullOrBlank()) return null
        val regex = Regex("""([0-9]+(?:\.[0-9]+)?)\s+out of 5""")
        return regex.find(text)?.groupValues?.get(1)?.toDoubleOrNull()
    }

    fun parseReviews(text: String?): Int? {
        if (text.isNullOrBlank()) return null
        val regex = Regex("""([0-9,]+)""")
        return regex.find(text)?.groupValues?.get(1)?.replace(",", "")?.toIntOrNull()
    }

    fun cleanText(text: String?): String? {
        if (text.isNullOrBlank()) return null
        return text.split(Regex("\\s+")).joinToString(" ").trim()
    }

    fun buildAbsoluteUrl(href: String?, baseDomain: String): String? {
        if (href.isNullOrBlank()) return null
        if (href.startsWith("http://") || href.startsWith("https://")) return href
        return "https://$baseDomain$href"
    }
}
