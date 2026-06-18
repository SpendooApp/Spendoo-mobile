package com.spendoo.chatbot.api

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

@Stable
interface ChatbotFeatureApi {
    operator fun invoke(): (NavKey) -> NavEntry<NavKey>
}
