package com.spendoo.identity.presentation.screen.addFollower

import com.spendoo.identity.domain.model.UserSearch

data class AddFollowerUiState(
    val searchCode: String = "",
    val isLoading: Boolean = false,
    val searchedUser: UserSearch? = null,
    val isFollowSent: Boolean = false
)
