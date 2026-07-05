package com.spendoo.identity.presentation.screen.followers

import com.spendoo.identity.domain.model.UserSearch

data class FollowersUiState(
    val isLoading: Boolean = false,
    val isPendingLoadingMore: Boolean = false,
    val isFollowersLoadingMore: Boolean = false,
    val pendingRequests: List<UserSearch> = emptyList(),
    val followers: List<UserSearch> = emptyList(),
    val isRefreshing: Boolean = false,
)
