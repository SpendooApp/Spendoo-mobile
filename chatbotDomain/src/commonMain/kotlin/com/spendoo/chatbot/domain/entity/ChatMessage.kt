package com.spendoo.chatbot.domain.entity

import kotlinx.datetime.LocalDateTime

data class ChatMessage(
    val id: String,
    val sender: ChatSender,
    val content: String,
    val timestamp: LocalDateTime
)
