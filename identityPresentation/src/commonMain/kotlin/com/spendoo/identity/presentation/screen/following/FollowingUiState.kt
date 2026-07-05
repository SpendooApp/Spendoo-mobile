package com.spendoo.identity.presentation.screen.following

import com.spendoo.identity.domain.model.UserSearch

data class FollowingUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val followings: List<UserSearch> = emptyList(),
    val isRefreshing: Boolean = false,
)
