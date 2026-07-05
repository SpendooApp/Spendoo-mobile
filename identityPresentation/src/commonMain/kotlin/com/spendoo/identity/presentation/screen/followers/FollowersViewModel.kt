package com.spendoo.identity.presentation.screen.followers

import androidx.lifecycle.viewModelScope
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.repository.FollowRepository
import com.spendoo.shared.domain.utils.PageQuery
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.unknown_error

class FollowersViewModel(
    private val followRepository: FollowRepository
) : BaseViewModel<FollowersUiState>(FollowersUiState()), FollowersInteractionListener {

    private val pendingRequestsPaginator = createPaginator(
        initialKey = 0,
        pageSize = 20,
        loadPage = { page ->
            followRepository.getPendingRequests(PageQuery(page = page, size = 20)).data
        },
        onSuccess = { items ->
            updateState { copy(pendingRequests = pendingRequests + items) }
        },
        onLoadUpdated = { isLoadingMore ->
            updateState { copy(isPendingLoadingMore = isLoadingMore) }
        },
        onError = { handleError(it) }
    )

    private val followersPaginator = createPaginator(
        initialKey = 0,
        pageSize = 20,
        loadPage = { page ->
            followRepository.getFollowers(PageQuery(page = page, size = 20)).data
        },
        onSuccess = { items ->
            updateState { copy(followers = followers + items) }
        },
        onLoadUpdated = { isLoadingMore ->
            updateState { copy(isFollowersLoadingMore = isLoadingMore) }
        },
        onError = { handleError(it) }
    )

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, isRefreshing = true) }
            pendingRequestsPaginator.reset()
            followersPaginator.reset()
            pendingRequestsPaginator.loadNextItems()
            followersPaginator.loadNextItems()
            updateState { copy(isLoading = false, isRefreshing = false) }
        }
    }

    override fun onReload() {
        loadData()
    }

    private fun handleError(throwable: Throwable?) {
        showSnackBar(
            title = UiText.StringRes(Res.string.an_error_occurred),
            message = throwable?.message?.let(UiText::DynamicString)
                ?: UiText.StringRes(Res.string.unknown_error),
            isSuccess = false
        )
    }

    override fun onClickBack() {
        popBackStack()
    }

    override fun onClickApprove(followerId: String) {
        tryToCall(
            block = { followRepository.respondToFollowRequest(followerId, isApproved = true) },
            onSuccess = {
                val approvedUser = state.value.pendingRequests.find { it.userId == followerId }
                updateState {
                    copy(
                        pendingRequests = pendingRequests.filter { it.userId != followerId },
                        followers = if (approvedUser != null) followers + approvedUser else followers
                    )
                }
            },
            onError = { handleError(it) }
        )
    }

    override fun onClickReject(followerId: String) {
        tryToCall(
            block = { followRepository.respondToFollowRequest(followerId, isApproved = false) },
            onSuccess = {
                updateState { copy(pendingRequests = pendingRequests.filter { it.userId != followerId }) }
            },
            onError = { handleError(it) }
        )
    }

    override fun onClickRemove(followerId: String) {
        tryToCall(
            block = { followRepository.removeFollower(followerId) },
            onSuccess = {
                updateState { copy(followers = followers.filter { it.userId != followerId }) }
            },
            onError = { handleError(it) }
        )
    }

    override fun onLoadMorePendingRequests() {
        viewModelScope.launch {
            updateState { copy(isPendingLoadingMore = true) }
            pendingRequestsPaginator.loadNextItems()
            updateState { copy(isPendingLoadingMore = false)}
        }
    }

    override fun onLoadMoreFollowers() {
        viewModelScope.launch {
            updateState { copy(isFollowersLoadingMore = true) }
            followersPaginator.loadNextItems()
            updateState { copy(isFollowersLoadingMore = false)}
        }
    }
}
