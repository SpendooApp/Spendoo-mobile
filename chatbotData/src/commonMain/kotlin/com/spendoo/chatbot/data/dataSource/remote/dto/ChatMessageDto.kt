package com.spendoo.chatbot.data.dataSource.remote.dto

import com.spendoo.chatbot.domain.entity.ChatMessage
import com.spendoo.chatbot.domain.entity.ChatSender
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageDto(
    @SerialName("id")
    val id: String,
    @SerialName("sender")
    val sender: ChatSender,
    @SerialName("content")
    val content: String,
    @SerialName("timestamp")
    val timestamp: String
)

fun ChatMessageDto.toDomain(): ChatMessage = ChatMessage(
    id = id,
    sender = sender,
    content = content,
    timestamp = LocalDateTime.parse(timestamp)
)
