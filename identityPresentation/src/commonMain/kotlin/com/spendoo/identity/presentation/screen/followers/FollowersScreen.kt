package com.spendoo.identity.presentation.screen.followers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import com.spendoo.identity.domain.model.UserSearch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.approve
import spendoo.designsystem.generated.resources.followers
import spendoo.designsystem.generated.resources.reject
import spendoo.designsystem.generated.resources.remove
import spendoo.designsystem.generated.resources.requests

@Composable
fun FollowersScreen(
    viewModel: FollowersViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    FollowersContent(
        state = state,
        listener = viewModel
    )
}

@Composable
fun FollowersContent(
    state: FollowersUiState,
    listener: FollowersInteractionListener
) {
    val listState = rememberLazyListState()

    PaginationTrigger(
        list = state.followers,
        listState = listState,
        remainingItemsToLoadNextPage = 3,
        loadNextItems = listener::onLoadMoreFollowers
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(
            title = stringResource(Res.string.followers),
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
                if (state.pendingRequests.isNotEmpty()) {
                    item {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = stringResource(Res.string.requests),
                            style = Theme.typography.body.large,
                            color = Theme.colorScheme.text.title
                        )
                    }

                    items(
                        items = state.pendingRequests,
                        key = { "req_${it.userId}" }
                    ) { user ->
                        ProfileFollowCard(
                            imageUrl = user.imageUrl.orEmpty(),
                            name = user.fullName,
                            onClick = {  },
                            actions = listOf(
                                {
                                    ActionButton(
                                        onActionClick = { listener.onClickApprove(user.userId) },
                                        text = stringResource(Res.string.approve),
                                        textColor = Theme.colorScheme.button.primary,
                                        backgroundColor = Theme.colorScheme.button.secondary
                                    )
                                },
                                {
                                    ActionButton(
                                        onActionClick = { listener.onClickReject(user.userId) },
                                        text = stringResource(Res.string.reject),
                                        textColor = Theme.colorScheme.additional.onWarning,
                                        backgroundColor = Theme.colorScheme.button.secondary
                                    )
                                }
                            )
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                item {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = stringResource(Res.string.followers),
                        style = Theme.typography.body.large,
                        color = Theme.colorScheme.text.title
                    )
                }

                items(
                    items = state.followers,
                    key = { "fol_${it.userId}" }
                ) { user ->
                    ProfileFollowCard(
                        imageUrl = user.imageUrl.orEmpty(),
                        name = user.fullName,
                        onClick = { },
                        actions = listOf {
                            ActionButton(
                                onActionClick = { listener.onClickRemove(user.userId) },
                                text = stringResource(Res.string.remove),
                                textColor = Theme.colorScheme.additional.onError,
                                backgroundColor = Theme.colorScheme.additional.error
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
private fun FollowersScreenPreview() {
    SpendooTheme {
        FollowersContent(
            state = FollowersUiState(
                pendingRequests = listOf(
                    UserSearch("1", "Joseph Sameh", null)
                ),
                followers = listOf(
                    UserSearch("2", "Ahmed Bassiouny", null),
                    UserSearch("3", "Asmaa Yasser", null)
                )
            ),
            listener = object : FollowersInteractionListener {
                override fun onClickBack() {}
                override fun onClickApprove(followerId: String) {}
                override fun onClickReject(followerId: String) {}
                override fun onClickRemove(followerId: String) {}
                override fun onLoadMorePendingRequests() {}
                override fun onLoadMoreFollowers() {}
                override fun onReload() {}
            }
        )
    }
}
