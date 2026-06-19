package com.spendoo.chatbot.presentation.api

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.spendoo.chatbot.api.ChatbotFeatureApi
import com.spendoo.chatbot.api.ChatbotRoute
import com.spendoo.chatbot.presentation.screen.chatbot.ChatbotScreen

@Stable
class ChatbotFeatureApiImpl : ChatbotFeatureApi {
    override fun invoke(): (NavKey) -> NavEntry<NavKey> {
        return entryProvider {
            entry<ChatbotRoute> { ChatbotScreen() }
        }
    }
}
