package com.spendoo.identity.presentation.screen.profile

import com.spendoo.identity.domain.util.AppLanguage

interface ProfileInteractionListener {
    fun onClickBack()
    fun onClickProfileDetails()
    fun onClickCategories()
    fun onClickScheduledPayments()
    fun onClickAchievements()
    fun onClickNotificationSetting()
    fun onClickViewAllFollowing()
    fun onClickViewAllFollowers()
    fun onOpenAddFollowerSheet()
    fun onDismissAddFollowerSheet()
    fun onClickRegenerateFollowCode()
    fun onClickCopyFollowCode()
    fun onClickChangeLanguage()
    fun onSelectLanguage(language: AppLanguage)
    fun onDismissLanguageBottomSheet()
    fun onToggleTheme(isDark: Boolean)
    fun onReload()
    fun onClickLogout()
    fun onClickUser(userId: String, userName: String, imageUrl: String?)
}
