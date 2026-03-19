package com.spendoo.identity.data.di

import com.spendoo.identity.data.utils.languageCode
import io.ktor.client.plugins.api.createClientPlugin

fun languageInterceptor() = createClientPlugin("LanguageInterceptor") {
    onRequest { request, _ ->
        request.headers.append("Accept-Language", languageCode)
    }
}
