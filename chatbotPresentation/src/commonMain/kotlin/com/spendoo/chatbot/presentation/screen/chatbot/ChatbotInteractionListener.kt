package com.spendoo.chatbot.presentation.screen.chatbot

interface ChatbotInteractionListener {
    fun onInputTextChange(text: String)
    fun onSendMessageClicked()
    fun onClearChatClicked()
    fun onBackClicked()
}
