package com.spendoo.identity.presentation.screen.profile

import com.spendoo.categories.api.CategoriesRoute
import com.spendoo.categories.api.ScheduledPaymentsRoute
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.goals.api.AchievementsRoute
import com.spendoo.home.api.NotificationsRoute
import com.spendoo.identity.api.EditProfileRoute
import com.spendoo.identity.api.FollowersRoute
import com.spendoo.identity.api.FollowingRoute
import com.spendoo.identity.domain.repository.FollowCodeRepository
import com.spendoo.identity.domain.repository.FollowRepository
import com.spendoo.identity.domain.repository.ProfileRepository
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.util.AppLanguage
import com.spendoo.identity.domain.util.AppTheme
import com.spendoo.shared.domain.utils.PageQuery
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.copied
import spendoo.designsystem.generated.resources.unknown_error

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    private val followRepository: FollowRepository,
    private val followCodeRepository: FollowCodeRepository,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel<ProfileUiState>(ProfileUiState()), ProfileInteractionListener {

    init {
        loadProfileData()
        observeSettings()
    }

    private fun loadProfileData() {
        updateState { copy(isRefreshing = true) }
        tryToCall(
            block = { profileRepository.getProfile() },
            onSuccess = { profile ->
                updateState {
                    copy(
                        userId = profile.id,
                        fullName = profile.fullName,
                        imageUrl = profile.imageUrl,
                        followCode = profile.code,
                        isRefreshing = false
                    )
                }
            },
            onError = { 
                updateState { copy(isRefreshing = false) }
                handleError(it) 
            }
        )

        tryToCall(
            block = { followRepository.getFollowing(PageQuery(page = 0, size = 10)) },
            onSuccess = { pagedData ->
                updateState { copy(followings = pagedData.data) }
            },
            onError = { handleError(it) }
        )

        tryToCall(
            block = { followRepository.getFollowers(PageQuery(page = 0, size = 10)) },
            onSuccess = { pagedData ->
                updateState { copy(followers = pagedData.data) }
            },
            onError = { handleError(it) }
        )
    }

    override fun onReload() {
        loadProfileData()
    }

    private fun observeSettings() {
        tryToCollect(
            block = { settingsRepository.observeAppTheme() },
            onEach = { },
            onError = { handleError(it) }
        )

        tryToCollect(
            block = { settingsRepository.observeAppLanguage() },
            onEach = { language ->
                updateState { copy(selectedLanguage = language) }
            },
            onError = { handleError(it) }
        )
    }

    private fun handleError(throwable: Throwable) {
        showSnackBar(
            title = UiText.StringRes(Res.string.an_error_occurred),
            message = throwable.message?.let(UiText::DynamicString)
                ?: UiText.StringRes(Res.string.unknown_error),
            isSuccess = false
        )
    }

    override fun onClickBack() {
        popBackStack()
    }

    override fun onClickProfileDetails() {
        navigate(EditProfileRoute)
    }

    override fun onClickCategories() {
        navigate(CategoriesRoute)
    }

    override fun onClickScheduledPayments() {
        navigate(ScheduledPaymentsRoute)
    }

    override fun onClickAchievements() {
        navigate(AchievementsRoute)
    }

    override fun onClickNotificationSetting() {
        navigate(NotificationsRoute)
    }

    override fun onClickViewAllFollowing() {
        navigate(FollowingRoute)
    }

    override fun onClickViewAllFollowers() {
        navigate(FollowersRoute)
    }

    override fun onOpenAddFollowerSheet() {
        updateState { copy(isAddFollowerSheetVisible = true) }
    }

    override fun onDismissAddFollowerSheet() {
        updateState { copy(isAddFollowerSheetVisible = false) }
    }

    override fun onClickRegenerateFollowCode() {
        tryToCall(
            block = { followCodeRepository.generateFollowCode() },
            onSuccess = { codeModel ->
                updateState { copy(followCode = codeModel.code) }
            },
            onError = { handleError(it) }
        )
    }

    override fun onClickCopyFollowCode() {
        showSnackBar(title = UiText.StringRes(Res.string.copied))
    }

    override fun onClickChangeLanguage() {
        updateState { copy(isLanguageBottomSheetVisible = true) }
    }

    override fun onSelectLanguage(language: AppLanguage) {
        tryToCall(
            block = { settingsRepository.applyLanguage(language) },
            onSuccess = {
                updateState { copy(selectedLanguage = language, isLanguageBottomSheetVisible = false) }
            },
            onError = { handleError(it) }
        )
    }

    override fun onDismissLanguageBottomSheet() {
        updateState { copy(isLanguageBottomSheetVisible = false) }
    }

    override fun onToggleTheme(isDark: Boolean) {
        val newTheme = if (isDark) AppTheme.DARK else AppTheme.LIGHT
        tryToCall(
            block = { settingsRepository.applyAppTheme(newTheme) },
            onSuccess = { },
            onError = { handleError(it) }
        )
    }
}
