package com.spendoo.identity.data.di

import com.spendoo.identity.data.repository.AuthenticationRepositoryImpl.Companion.LOGIN_ENDPOINT
import com.spendoo.identity.data.repository.AuthenticationRepositoryImpl.Companion.REFRESH_ENDPOINT
import com.spendoo.identity.data.repository.RegisterRepositoryImpl.Companion.REGISTER
import com.spendoo.identity.data.repository.RegisterRepositoryImpl.Companion.REGISTER_REQUEST_OTP
import com.spendoo.identity.data.repository.RegisterRepositoryImpl.Companion.REGISTER_VERIFY_OTP
import com.spendoo.identity.data.repository.ResetPasswordRepositoryImpl.Companion.RESET_PASSWORD
import com.spendoo.identity.data.repository.ResetPasswordRepositoryImpl.Companion.RESET_PASSWORD_REQUEST_OTP
import com.spendoo.identity.data.repository.ResetPasswordRepositoryImpl.Companion.RESET_PASSWORD_VERIFY_OTP
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.service.AuthorizationService
import com.spendoo.shared.domain.navigation.GlobalNavigationHandler
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
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
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json

internal fun provideHttpClient(
    baseUrl: String,
    authorizationService: suspend () -> AuthorizationService,
    settingsRepository: () -> SettingsRepository,
    globalNavigationHandler: GlobalNavigationHandler,
): HttpClient {

    val prettyJson = Json {
        prettyPrint = true
        isLenient = true
    }

    return createHttpClient {
        expectSuccess = true

        HttpResponseValidator {
            validateResponse { response ->
                if (response.status == HttpStatusCode.PaymentRequired) {
                    globalNavigationHandler.onPaymentRequiredError()
                }
            }
        }

        defaultRequest {
            url(baseUrl)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(languageThemeInterceptor(settingsRepository))

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
                    // Find where the JSON structural markers actually begin and end
                    val firstBrace = message.indexOfAny(charArrayOf('{', '['))
                    val lastBrace = message.lastIndexOfAny(charArrayOf('}', ']'))

                    val formattedMessage = if (firstBrace != -1 && lastBrace != -1 && lastBrace > firstBrace) {
                        try {
                            // Extract only the raw JSON payload hidden inside the message strings
                            val rawJson = message.substring(firstBrace, lastBrace + 1)

                            // Parse and format it cleanly
                            val jsonElement = prettyJson.parseToJsonElement(rawJson)
                            val prettyJsonString = prettyJson.encodeToString(jsonElement)

                            // Re-assemble the log message, swapping out the flat line for the beautiful block
                            message.substring(0, firstBrace) + prettyJsonString + message.substring(lastBrace + 1)
                        } catch (_: Exception) {
                            message // Fallback to raw text if it wasn't valid JSON after all
                        }
                    } else {
                        message // Standard headers/URLs flow right through untouched
                    }

                    println("Identity Client:\n$formattedMessage")
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
                    val currentRefreshToken = authorizationService().getRefreshToken()
                    if (currentRefreshToken.isBlank()) {
                        return@refreshTokens null
                    }

                    return@refreshTokens try {
                        BearerTokens(
                            accessToken = authorizationService().getNewAccessToken(),
                            refreshToken = currentRefreshToken,
                        )
                    } catch (e: CancellationException) {
                        throw e
                    } catch (_: Exception) {
                        null
                    }
                }
                sendWithoutRequest { request ->
                    val path = request.url.encodedPath.removePrefix("/")
                    path !in whiteListEndPoints
                }
            }
        }
        install(HttpTimeout) {
            connectTimeoutMillis = CONNECT_TIMEOUT_MS
            requestTimeoutMillis = REQUEST_TIMEOUT_MS
        }
    }
}

internal fun provideCoilClient(): HttpClient {
    return createHttpClient {
        install(HttpTimeout) {
            connectTimeoutMillis = CONNECT_TIMEOUT_MS
            requestTimeoutMillis = REQUEST_TIMEOUT_MS
        }
    }
}

const val CONNECT_TIMEOUT_MS = 30_000L
const val REQUEST_TIMEOUT_MS = 60_000L
private val whiteListEndPoints = listOf(
    LOGIN_ENDPOINT,
    REFRESH_ENDPOINT,
    RESET_PASSWORD_REQUEST_OTP,
    RESET_PASSWORD_VERIFY_OTP,
    RESET_PASSWORD,
    REGISTER_REQUEST_OTP,
    REGISTER_VERIFY_OTP,
    REGISTER
)
