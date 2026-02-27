package com.spendoo.identity.data.utils

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.http.HttpStatusCode
import io.ktor.util.network.UnresolvedAddressException
import com.spendoo.identity.domain.exception.InvalidCredentialsException
import com.spendoo.identity.domain.exception.InvalidRequestException
import com.spendoo.identity.domain.exception.NoNetworkException
import com.spendoo.identity.domain.exception.PhoneNumberAlreadyExistsException
import com.spendoo.identity.domain.exception.TooManyRequestsException
import com.spendoo.identity.domain.exception.UnAuthorizedException
import com.spendoo.identity.domain.exception.UnknownException
import com.spendoo.identity.domain.exception.UserIsBlockedException

suspend fun <T> safeWrapper(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: ClientRequestException) {
        when (e.response.status) {
            HttpStatusCode.Unauthorized -> throw UnAuthorizedException()
            HttpStatusCode.NotFound -> throw InvalidCredentialsException()
            HttpStatusCode.Forbidden -> throw UserIsBlockedException()
            HttpStatusCode.TooManyRequests -> throw TooManyRequestsException()
            HttpStatusCode.BadRequest -> throw InvalidRequestException()
            HttpStatusCode.Conflict -> throw PhoneNumberAlreadyExistsException()
            else -> throw UnknownException()
        }
    } catch (e: Exception) {
        when(e){
            is UnresolvedAddressException -> throw NoNetworkException()
            is HttpRequestTimeoutException -> throw NoNetworkException()
            else -> throw e
        }
    }
}
