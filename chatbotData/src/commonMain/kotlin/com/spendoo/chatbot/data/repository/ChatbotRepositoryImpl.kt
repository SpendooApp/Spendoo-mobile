package com.spendoo.chatbot.data.repository

import com.spendoo.chatbot.data.dataSource.remote.dto.ChatMessageDto
import com.spendoo.chatbot.data.dataSource.remote.dto.ChatMessageRequestDto
import com.spendoo.chatbot.data.dataSource.remote.dto.toDomain
import com.spendoo.chatbot.domain.entity.ChatMessage
import com.spendoo.chatbot.domain.repository.ChatbotRepository
import com.spendoo.shared.data.dataSource.remote.dto.BasePagedData
import com.spendoo.shared.data.dataSource.remote.dto.toPagedData
import com.spendoo.shared.data.shared.BaseGateway
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.shared.domain.utils.PagedData
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ChatbotRepositoryImpl(
    client: HttpClient
) : BaseGateway(client), ChatbotRepository {

    override suspend fun sendMessage(content: String): ChatMessage {
        val response = tryToExecute<ChatMessageDto> {
            post("api/v1/chatbot/send") {
                setBody(ChatMessageRequestDto(content = content))
            }
        }
        return response.toDomain()
    }

    override suspend fun getChatHistory(query: PageQuery): PagedData<ChatMessage> {
        val response = tryToExecute<BasePagedData<ChatMessageDto>> {
            get("api/v1/chatbot/history") {
                parameter("page", query.page)
                parameter("size", query.size)
            }
        }
        return response.toPagedData { it.toDomain() }
    }

    override suspend fun clearChat() {
        tryToExecute<Unit> {
            delete("api/v1/chatbot/clear")
        }
    }
}
