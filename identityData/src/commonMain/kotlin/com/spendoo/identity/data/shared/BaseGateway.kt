package com.spendoo.identity.data.shared

import com.spendoo.identity.domain.exception.InternetException
import com.spendoo.identity.domain.exception.InvalidCredentialsException
import com.spendoo.identity.domain.exception.InvalidRequestException
import com.spendoo.identity.domain.exception.NoNetworkException
import com.spendoo.identity.domain.exception.EmailAlreadyExistsException
import com.spendoo.identity.domain.exception.TooManyRequestsException
import com.spendoo.identity.domain.exception.UnAuthorizedException
import com.spendoo.identity.domain.exception.UnknownErrorException
import com.spendoo.identity.domain.exception.UserIsBlockedException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException

abstract class BaseGateway(val client: HttpClient) {
    suspend inline fun <reified T> tryToExecute(method: HttpClient.() -> HttpResponse): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorResponse = e.response.body<ErrorResponse>()

            when (e.response.status) {
                HttpStatusCode.Unauthorized -> throw UnAuthorizedException()
                HttpStatusCode.NotFound -> throw InvalidCredentialsException()
                HttpStatusCode.Forbidden -> throw UserIsBlockedException()
                HttpStatusCode.TooManyRequests -> throw TooManyRequestsException()
                HttpStatusCode.BadRequest -> throw InvalidRequestException()
                HttpStatusCode.Conflict -> throw EmailAlreadyExistsException()
                else -> throw UnknownErrorException("HTTP ${errorResponse.status}: ${errorResponse.message}")
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