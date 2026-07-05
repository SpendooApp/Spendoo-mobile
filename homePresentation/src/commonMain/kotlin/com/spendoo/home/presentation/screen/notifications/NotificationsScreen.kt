package com.spendoo.home.presentation.screen.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.cards.NotificationCard
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import com.spendoo.notifications.domain.entity.Notification
import com.spendoo.notifications.domain.entity.NotificationType
import kotlinx.datetime.atTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_bell
import spendoo.designsystem.generated.resources.notifications

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    NotificationsContent(
        state = state,
        listener = viewModel
    )
}

@Composable
fun NotificationsContent(
    state: NotificationsUiState,
    listener: NotificationsInteractionListener
) {
    val listState = rememberLazyListState()

    PaginationTrigger(
        list = state.notifications,
        listState = listState,
        remainingItemsToLoadNextPage = 3,
        loadNextItems = listener::onLoadMoreNotifications
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(
            title = stringResource(Res.string.notifications),
            onBackClicked = listener::onClickBack
        )

        PullToRefresh(
            isRefreshing = state.isRefreshing,
            onRefresh = listener::onReload
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = state.notifications,
                    key = { it.id }
                ) { notification ->
                    val dateString = "${notification.sentAt.date.month.name.take(3)} ${notification.sentAt.date.day}"
                    
                    NotificationCard(
                        icon = Res.drawable.ic_bell,
                        title = notification.title,
                        description = notification.message,
                        current = dateString,
                        backgroundColor = if (notification.isRead) Theme.colorScheme.background.primary else Theme.colorScheme.background.secondary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NotificationsScreenPreview() {
    SpendooTheme {
        NotificationsContent(
            state = NotificationsUiState(
                notifications = listOf(
                    Notification(
                        id = "1",
                        title = "Budget Exceeded!",
                        message = "You’ve exceeded your shopping budget by 20% this month. Please review your spending.",
                        type = NotificationType.ALERT,
                        sentAt = kotlinx.datetime.LocalDate(2025, 1, 1).atTime(0, 0),
                        isRead = false
                    )
                )
            ),
            listener = object : NotificationsInteractionListener {
                override fun onClickBack() {}
                override fun onLoadMoreNotifications() {}
                override fun onReload() {}
            }
        )
    }
}
