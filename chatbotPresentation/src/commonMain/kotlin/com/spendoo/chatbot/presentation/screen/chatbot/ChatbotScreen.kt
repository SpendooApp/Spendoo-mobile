package com.spendoo.chatbot.presentation.screen.chatbot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject

@Composable
fun ChatbotScreen(
    viewModel: ChatbotViewModel = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ChatbotContent(state, viewModel)
}

@Composable
private fun ChatbotContent(
    state: ChatbotUiState,
    listener: ChatbotInteractionListener
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chatbot Screen")
    }
}
