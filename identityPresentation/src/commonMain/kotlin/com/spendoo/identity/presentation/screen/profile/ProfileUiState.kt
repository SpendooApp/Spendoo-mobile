package com.spendoo.identity.presentation.screen.profile

import com.spendoo.identity.domain.model.UserSearch
import com.spendoo.identity.domain.util.AppLanguage

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userId: String = "",
    val fullName: String = "",
    val imageUrl: String? = null,
    val followCode: String = "",
    val followings: List<UserSearch> = emptyList(),
    val followers: List<UserSearch> = emptyList(),
    val isFollowingsLoading: Boolean = false,
    val isFollowersLoading: Boolean = false,
    val isAddFollowerSheetVisible: Boolean = false,
    val selectedLanguage: AppLanguage = AppLanguage.ENGLISH,
    val isLanguageBottomSheetVisible: Boolean = false,
    val isRefreshing: Boolean = false,
    val isReminderEnabled: Boolean = true,
    val isHomeOffersEnabled: Boolean = true,
)
