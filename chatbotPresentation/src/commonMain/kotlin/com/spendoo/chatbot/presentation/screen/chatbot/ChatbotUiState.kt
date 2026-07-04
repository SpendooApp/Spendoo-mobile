package com.spendoo.chatbot.presentation.screen.chatbot

import com.spendoo.chatbot.domain.entity.ChatSender

data class ChatMessageItemUiState(
    val id: String,
    val sender: ChatSender,
    val content: String,
    val timestamp: String = ""
)

data class ChatbotUiState(
    val messages: List<ChatMessageItemUiState> = emptyList(),
    val inputText: String = "",
    val isLoadingHistory: Boolean = false,
    val isSendingMessage: Boolean = false,
    val isBotTyping: Boolean = false
) {
    val isInitialEmptyState: Boolean
        get() = messages.isEmpty() && !isSendingMessage && !isBotTyping && !isLoadingHistory
}
