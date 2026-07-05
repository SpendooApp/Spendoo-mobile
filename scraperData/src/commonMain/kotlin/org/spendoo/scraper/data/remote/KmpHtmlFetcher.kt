package org.spendoo.scraper.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.random.Random

interface KmpHtmlFetcher {
    suspend fun fetchHtml(
        url: String, 
        params: Map<String, String>, 
        headers: Map<String, String>, 
        retries: Int = 3, 
        backoffFactor: Int = 1
    ): String
}

class KtorAmazonFetcher(private val client: HttpClient = HttpClient()) : KmpHtmlFetcher {

    override suspend fun fetchHtml(
        url: String,
        params: Map<String, String>,
        headers: Map<String, String>,
        retries: Int,
        backoffFactor: Int
    ): String {
        var lastException: Exception? = null

        for (i in 0 until retries) {
            // Random delay matching your sleep strategy
            delay(Random.nextLong(1000, 5000))

            try {
                val response: HttpResponse = client.get(url) {
                    // Inject Default Headers + Custom Language Headers
                    AmazonConstants.DEFAULT_HEADERS.forEach { (k, v) -> header(k, v) }
                    headers.forEach { (k, v) -> header(k, v) }
                    
                    // Rotate User Agent dynamically on each retry iteration
                    header("User-Agent", AmazonConstants.USER_AGENTS.random())

                    // Populate URL parameters securely without manual building
                    url {
                        params.forEach { (k, v) -> parameters.append(k, v) }
                    }
                }

                if (response.status == HttpStatusCode.OK) {
                    return response.bodyAsText()
                } else if (response.status.value in listOf(429, 503)) {
                    val waitTime = (backoffFactor * 2.0.pow(i).toLong()) * 1000L
                    println("Received status code ${response.status.value}. Retrying in ${waitTime / 1000} seconds...")
                    delay(waitTime)
                } else {
                    throw RuntimeException("HTTP Server error status code: ${response.status.value}")
                }
            } catch (e: Exception) {
                lastException = e
                println("Request failed: ${e.message}. Retrying...")
                delay((backoffFactor * 2.0.pow(i).toLong()) * 1000L)
            }
        }
        throw RuntimeException("Failed to fetch HTML after $retries retries for URL: $url", lastException)
    }
}
