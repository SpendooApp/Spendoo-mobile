package com.spendoo.chatbot.data.dataSource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageRequestDto(
    @SerialName("content")
    val content: String
)
