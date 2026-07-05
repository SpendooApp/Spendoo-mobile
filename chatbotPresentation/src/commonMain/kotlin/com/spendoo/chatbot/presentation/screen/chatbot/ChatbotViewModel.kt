package com.spendoo.chatbot.presentation.screen.chatbot

import com.spendoo.chatbot.domain.entity.ChatSender
import com.spendoo.chatbot.domain.repository.ChatbotRepository
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.shared.domain.utils.PageQuery
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.could_not_clear_chat_history
import spendoo.designsystem.generated.resources.could_not_load_chat_history
import spendoo.designsystem.generated.resources.could_not_send_message
import kotlin.time.Clock

class ChatbotViewModel(
    private val chatbotRepository: ChatbotRepository
) : BaseViewModel<ChatbotUiState>(ChatbotUiState()), ChatbotInteractionListener {

    init {
        loadChatHistory()
    }

    private fun loadChatHistory() {
        updateState { it.copy(isLoadingHistory = true) }
        tryToCall(
            block = { chatbotRepository.getChatHistory(PageQuery(page = 0, size = 50)) },
            onSuccess = { history ->
                updateState {
                    it.copy(
                        messages = history.data.map { msg ->
                            ChatMessageItemUiState(
                                id = msg.id,
                                sender = msg.sender,
                                content = msg.content,
                                timestamp = msg.timestamp.toString()
                            )
                        },
                        isLoadingHistory = false
                    )
                }
            },
            onError = {
                updateState { copy(isLoadingHistory = false) }
                showSnackBar(
                    title = UiText.StringRes(Res.string.could_not_load_chat_history),
                    message = it.message?.let { message -> UiText.DynamicString(message) },
                    isSuccess = false
                )
            }
        )
    }

    override fun onInputTextChange(text: String) {
        updateState { it.copy(inputText = text) }
    }

    override fun onSendMessageClicked() {
        val currentInput = state.value.inputText.trim()
        if (currentInput.isEmpty() || state.value.isSendingMessage) return

        val tempId = "temp_${Clock.System.now().toEpochMilliseconds()}"
        val userMessage = ChatMessageItemUiState(
            id = tempId,
            sender = ChatSender.USER,
            content = currentInput
        )

        updateState {
            it.copy(
                messages = it.messages + userMessage,
                inputText = "",
                isSendingMessage = true,
                isBotTyping = true
            )
        }

        tryToCall(
            block = { chatbotRepository.sendMessage(currentInput) },
            onSuccess = { botResponse ->
                val botMessage = ChatMessageItemUiState(
                    id = botResponse.id,
                    sender = botResponse.sender,
                    content = botResponse.content,
                    timestamp = botResponse.timestamp.toString()
                )
                updateState {
                    it.copy(
                        messages = it.messages + botMessage,
                        isSendingMessage = false,
                        isBotTyping = false
                    )
                }
            },
            onError = {
                updateState {
                    it.copy(
                        isSendingMessage = false,
                        isBotTyping = false
                    )
                }
                showSnackBar(
                    title = UiText.StringRes(Res.string.could_not_send_message),
                    message = it.message?.let { message -> UiText.DynamicString(message) },
                    isSuccess = false
                )
            }
        )
    }

    override fun onClearChatClicked() {
        tryToCall(
            block = { chatbotRepository.clearChat() },
            onSuccess = {
                updateState { it.copy(messages = emptyList()) }
            },
            onError = {
                showSnackBar(
                    title = UiText.StringRes(Res.string.could_not_clear_chat_history),
                    message = it.message?.let { message -> UiText.DynamicString(message) },
                    isSuccess = false
                )
            }
        )
    }

    override fun onBackClicked() {
        popBackStack()
    }
}
