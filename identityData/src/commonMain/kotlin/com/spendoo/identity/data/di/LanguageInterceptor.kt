package com.spendoo.identity.data.di

import com.spendoo.identity.data.utils.languageCode
import io.ktor.client.plugins.api.createClientPlugin

fun languageInterceptor() = createClientPlugin("LanguageInterceptor") {
    onRequest { request, _ ->
        if (!request.headers.contains("Accept-Language")) {
            request.headers.append("Accept-Language", languageCode)
        }
    }
}
