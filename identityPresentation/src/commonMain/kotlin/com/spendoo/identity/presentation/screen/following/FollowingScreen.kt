package com.spendoo.identity.presentation.screen.following

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
import com.spendoo.designsystem.components.cards.ActionButton
import com.spendoo.designsystem.components.cards.ProfileFollowCard
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import com.spendoo.identity.domain.model.UserSearch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.following
import spendoo.designsystem.generated.resources.unfollow

@Composable
fun FollowingScreen(
    viewModel: FollowingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    FollowingContent(
        state = state,
        listener = viewModel
    )
}

@Composable
fun FollowingContent(
    state: FollowingUiState,
    listener: FollowingInteractionListener
) {
    val listState = rememberLazyListState()

    PaginationTrigger(
        list = state.followings,
        listState = listState,
        remainingItemsToLoadNextPage = 3,
        loadNextItems = listener::onLoadMoreFollowing
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(
            title = stringResource(Res.string.following),
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = state.followings,
                    key = { it.userId }
                ) { user ->
                    ProfileFollowCard(
                        imageUrl = user.imageUrl.orEmpty(),
                        name = user.fullName,
                        onClick = { listener.onClickUser(user.userId, user.fullName, user.imageUrl) },
                        actions = listOf {
                            ActionButton(
                                onActionClick = { listener.onClickUnfollow(user.userId) },
                                text = stringResource(Res.string.unfollow),
                                textColor = Theme.colorScheme.button.primary,
                                backgroundColor = Theme.colorScheme.button.secondary
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FollowingScreenPreview() {
    SpendooTheme {
        FollowingContent(
            state = FollowingUiState(
                followings = listOf(
                    UserSearch("1", "Joseph Sameh", null),
                    UserSearch("2", "Salma Mohamed", null)
                )
            ),
            listener = object : FollowingInteractionListener {
                override fun onClickBack() {}
                override fun onClickUnfollow(userId: String) {}
                override fun onLoadMoreFollowing() {}
                override fun onReload() {}
                override fun onClickUser(userId: String, userName: String, imageUrl: String?) {}
            }
        )
    }
}
