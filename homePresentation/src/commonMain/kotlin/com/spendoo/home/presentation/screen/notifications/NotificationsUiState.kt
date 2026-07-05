package com.spendoo.home.presentation.screen.notifications

import com.spendoo.notifications.domain.entity.Notification

data class NotificationsUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val notifications: List<Notification> = emptyList()
)
