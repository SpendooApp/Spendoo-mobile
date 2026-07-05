package com.spendoo.shared.data.shared

import com.spendoo.shared.domain.exception.EmailAlreadyExistsException
import com.spendoo.shared.domain.exception.InternetException
import com.spendoo.shared.domain.exception.InvalidCredentialsException
import com.spendoo.shared.domain.exception.InvalidRequestException
import com.spendoo.shared.domain.exception.NoNetworkException
import com.spendoo.shared.domain.exception.TooManyRequestsException
import com.spendoo.shared.domain.exception.UnAuthorizedException
import com.spendoo.shared.domain.exception.UnknownErrorException
import com.spendoo.shared.domain.exception.UserIsBlockedException
import com.spendoo.shared.domain.navigation.GlobalNavigationHandler
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseGateway(val client: HttpClient): KoinComponent {
    val globalNavigationHandler: GlobalNavigationHandler by inject()

    suspend inline fun <reified T> tryToExecute(method: HttpClient.() -> HttpResponse): T {
        try {
            return client.method().body()
        } catch (e: ResponseException) {
            val status = e.response.status
            val errorResponse = runCatching { e.response.body<ErrorResponse>() }.getOrNull()
            val message = errorResponse?.message ?: e.message ?: "Request failed"

            when {
                status == HttpStatusCode.PaymentRequired -> {
                    globalNavigationHandler.onPaymentRequiredError()
                    throw CancellationException(message = "Payment required.", cause = e)
                }
                status == HttpStatusCode.Unauthorized -> throw UnAuthorizedException()
                status == HttpStatusCode.NotFound -> throw InvalidCredentialsException()
                status == HttpStatusCode.Forbidden -> throw UserIsBlockedException()
                status == HttpStatusCode.TooManyRequests -> throw TooManyRequestsException()
                status == HttpStatusCode.BadRequest -> throw InvalidRequestException()
                status == HttpStatusCode.Conflict -> throw EmailAlreadyExistsException()
                status.value in 400..499 -> throw InvalidRequestException()
                status.value in 500..599 -> throw UnknownErrorException(message)
                else -> throw UnknownErrorException(message)
            }

        } catch (e: InternetException.NoInternetException) {
            throw e
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            when (e) {
                is UnresolvedAddressException -> throw NoNetworkException()
                is HttpRequestTimeoutException -> throw NoNetworkException()
                else -> throw UnknownErrorException(e.message.toString())
            }
        }
    }
}
