package com.spendoo.chatbot.data.di

import com.spendoo.chatbot.data.repository.ChatbotRepositoryImpl
import com.spendoo.chatbot.domain.repository.ChatbotRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatbotDataModule = module {
    singleOf(::ChatbotRepositoryImpl) bind ChatbotRepository::class
}
