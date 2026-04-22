package com.spendoo.categories.data.di

import com.spendoo.identity.domain.service.AuthorizationService
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun provideCategoriesHttpClient(
    engine: HttpClientEngine,
    baseUrl: String,
    authorizationService: suspend () -> AuthorizationService,
): HttpClient {
    return HttpClient(engine) {
        expectSuccess = true

        defaultRequest {
            url(baseUrl)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("Categories Client: $message")
                }
            }
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = authorizationService().getAccessToken(),
                        refreshToken = authorizationService().getRefreshToken(),
                    )
                }
                refreshTokens {
                    BearerTokens(
                        accessToken = authorizationService().getNewAccessToken(),
                        refreshToken = authorizationService().getRefreshToken(),
                    )
                }
                sendWithoutRequest { false }
            }
        }

        install(HttpTimeout) {
            connectTimeoutMillis = NETWORK_TIMEOUT_MS
            requestTimeoutMillis = NETWORK_TIMEOUT_MS
        }
    }
}

const val NETWORK_TIMEOUT_MS = 15_000L

