package com.spendoo.chatbot.domain.repository

import com.spendoo.chatbot.domain.entity.ChatMessage
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData

interface ChatbotRepository {
    suspend fun sendMessage(content: String): ChatMessage
    suspend fun getChatHistory(query: PageQuery): PagedData<ChatMessage>
    suspend fun clearChat()
}
