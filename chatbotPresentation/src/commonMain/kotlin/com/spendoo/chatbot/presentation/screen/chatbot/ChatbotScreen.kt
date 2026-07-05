package com.spendoo.chatbot.presentation.screen.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.chatbot.domain.entity.ChatSender
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.indicator.DotsProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ask_ai_anything
import spendoo.designsystem.generated.resources.hey_i_m_spendoo_bot
import spendoo.designsystem.generated.resources.ic_delete
import spendoo.designsystem.generated.resources.ic_send
import spendoo.designsystem.generated.resources.img_chatbot
import spendoo.designsystem.generated.resources.let_simplify_your_financial_journey

@Composable
fun ChatbotScreen(
    viewModel: ChatbotViewModel = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ChatbotContent(state = state, listener = viewModel)
}

@Composable
private fun ChatbotContent(
    state: ChatbotUiState,
    listener: ChatbotInteractionListener
) {
    val listState = rememberLazyListState()

    LaunchedEffect(state.messages.size, state.isBotTyping) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        TopBar(
            title = "",
            onBackClicked = listener::onBackClicked,
            modifier = Modifier.fillMaxWidth(),
            actions = listOf(
                {
                    if (state.messages.isNotEmpty()) {
                        SpendooIconButton(
                            iconRes = Res.drawable.ic_delete,
                            contentDescription = "Clear Chat History",
                            onClick = listener::onClearChatClicked,
                        )
                    }
                }
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (state.isInitialEmptyState) {
                InitialEmptyStateLayout()
            } else {
                ChatMessagesListLayout(
                    messages = state.messages,
                    isBotTyping = state.isBotTyping,
                    listState = listState
                )
            }
        }

        ChatInputBar(
            inputText = state.inputText,
            onInputTextChange = listener::onInputTextChange,
            onSendClicked = listener::onSendMessageClicked
        )
    }
}

@Composable
private fun InitialEmptyStateLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(Res.drawable.img_chatbot),
            contentDescription = "Spendoo Bot",
            modifier = Modifier.size(280.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = Res.string.hey_i_m_spendoo_bot.asString(),
            style = Theme.typography.heading.small.copy(fontSize = 20.sp),
            color = Theme.colorScheme.button.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = Res.string.let_simplify_your_financial_journey.asString(),
            style = Theme.typography.title.small,
            color = Theme.colorScheme.brand.secondaryVariant
        )
    }
}

@Composable
private fun ChatMessagesListLayout(
    messages: List<ChatMessageItemUiState>,
    isBotTyping: Boolean,
    listState: LazyListState
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(messages, key = { it.id }) { message ->
            if (message.sender == ChatSender.USER) {
                UserChatMessageBubble(message = message)
            } else {
                BotChatMessageItem(message = message)
            }
        }

        if (isBotTyping) {
            item {
                DotsProgressIndicator()
            }
        }
    }
}

@Composable
private fun UserChatMessageBubble(
    message: ChatMessageItemUiState
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomStart = 24.dp,
                        bottomEnd = 4.dp
                    )
                )
                .background(Theme.colorScheme.button.secondary)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = message.content,
                style = Theme.typography.title.small.copy(fontSize = 16.sp),
                color = Theme.colorScheme.text.title
            )
        }
    }
}

@Composable
private fun BotChatMessageItem(
    message: ChatMessageItemUiState
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = message.content,
            style = Theme.typography.title.small.copy(fontSize = 16.sp),
            color = Theme.colorScheme.text.title,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
private fun ChatInputBar(
    inputText: String,
    onInputTextChange: (String) -> Unit,
    onSendClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CustomTextField(
            value = inputText,
            onValueChange = onInputTextChange,
            modifier = Modifier
                .fillMaxWidth(),
            hint = Res.string.ask_ai_anything.asString(),
            trailingIcon = painterResource(Res.drawable.ic_send),
            trailingIconColor = Theme.colorScheme.icon.primary,
            backgroundColor = Theme.colorScheme.background.secondary,
            textColor = Theme.colorScheme.text.title,
            textStyle = Theme.typography.body.medium.copy(fontSize = 14.sp),
            singleLine = false,
            onTrailingIconClick = {
                onSendClicked()
            },
            shape = RoundedCornerShape(30.dp)
        )
    }
}
