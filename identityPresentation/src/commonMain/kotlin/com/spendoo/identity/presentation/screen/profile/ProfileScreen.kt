package com.spendoo.identity.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.createClipEntry
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.domain.model.UserSearch
import com.spendoo.identity.domain.util.AppLanguage
import com.spendoo.identity.presentation.screen.addFollower.AddFollowerBottomSheet
import com.spendoo.identity.presentation.screen.profile.components.FollowersSection
import com.spendoo.identity.presentation.screen.profile.components.FollowingSection
import com.spendoo.identity.presentation.screen.profile.components.LanguageSelectionBottomSheet
import com.spendoo.identity.presentation.screen.profile.components.ProfileHeader
import com.spendoo.identity.presentation.screen.profile.components.ProfileSettingsList
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val clipboard = LocalClipboard.current

    val scope = rememberCoroutineScope()

    ProfileContent(
        state = state,
        listener = viewModel,
        onCopyFollowCode = {
            scope.launch {
                clipboard.setClipEntry(
                    clipEntry = createClipEntry(
                        text = state.followCode.ifBlank { state.userId },
                        label = "Follow Code"
                    )
                )
            }
            viewModel.onClickCopyFollowCode()
        }
    )

    if (state.isAddFollowerSheetVisible) {
        AddFollowerBottomSheet(
            onDismiss = viewModel::onDismissAddFollowerSheet
        )
    }

    if (state.isLanguageBottomSheetVisible) {
        LanguageSelectionBottomSheet(
            selectedLanguage = state.selectedLanguage,
            onSelectLanguage = viewModel::onSelectLanguage,
            onDismiss = viewModel::onDismissLanguageBottomSheet
        )
    }
}

@Composable
fun ProfileContent(
    state: ProfileUiState,
    listener: ProfileInteractionListener,
    onCopyFollowCode: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        TopBar(
            title = "",
            onBackClicked = listener::onClickBack
        )

        PullToRefresh(
            isRefreshing = state.isRefreshing,
            onRefresh = listener::onReload
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileHeader(state = state, onCopyFollowCode = onCopyFollowCode, onRegenerateCode = listener::onClickRegenerateFollowCode)

                Spacer(modifier = Modifier.height(24.dp))

                FollowingSection(state = state, listener = listener)

                Spacer(modifier = Modifier.height(20.dp))

                FollowersSection(state = state, listener = listener)

                Spacer(modifier = Modifier.height(24.dp))

                ProfileSettingsList(listener = listener)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    SpendooTheme {
        ProfileContent(
            state = ProfileUiState(
                fullName = "Ali Ahmed",
                followCode = "2564266585",
                followings = listOf(
                    UserSearch("1", "Joseph", null),
                    UserSearch("2", "Salma", null)
                ),
                followers = listOf(
                    UserSearch("3", "Nour", null),
                    UserSearch("4", "Asmaa", null)
                )
            ),
            listener = object : ProfileInteractionListener {
                override fun onClickBack() {}
                override fun onClickProfileDetails() {}
                override fun onClickCategories() {}
                override fun onClickScheduledPayments() {}
                override fun onClickAchievements() {}
                override fun onClickNotificationSetting() {}
                override fun onClickViewAllFollowing() {}
                override fun onClickViewAllFollowers() {}
                override fun onOpenAddFollowerSheet() {}
                override fun onDismissAddFollowerSheet() {}
                override fun onClickRegenerateFollowCode() {}
                override fun onClickCopyFollowCode() {}
                override fun onClickChangeLanguage() {}
                override fun onSelectLanguage(language: AppLanguage) {}
                override fun onDismissLanguageBottomSheet() {}
                override fun onToggleTheme(isDark: Boolean) {}
                override fun onReload() {}
            }
        )
    }
}
