package com.spendoo.chatbot.presentation.di

import com.spendoo.chatbot.api.ChatbotFeatureApi
import com.spendoo.chatbot.presentation.api.ChatbotFeatureApiImpl
import com.spendoo.chatbot.presentation.screen.chatbot.ChatbotViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatbotPresentationModule = module {
    factoryOf(::ChatbotViewModel)
}
