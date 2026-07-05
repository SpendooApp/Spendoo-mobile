package com.spendoo.identity.presentation.screen.following

import androidx.lifecycle.viewModelScope
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.repository.FollowRepository
import com.spendoo.shared.domain.utils.PageQuery
import com.spendoo.statistics.api.StatisticsRoute
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.unknown_error

class FollowingViewModel(
    private val followRepository: FollowRepository
) : BaseViewModel<FollowingUiState>(FollowingUiState()), FollowingInteractionListener {

    private val followingPaginator = createPaginator(
        initialKey = 0,
        pageSize = 20,
        loadPage = { page ->
            followRepository.getFollowing(PageQuery(page = page, size = 20)).data
        },
        onSuccess = { items ->
            updateState { copy(followings = followings + items) }
        },
        onLoadUpdated = { isLoadingMore ->
            updateState { copy(isLoadingMore = isLoadingMore) }
        },
        onError = { handleError(it) }
    )

    init {
        loadFollowingList()
    }

    private fun loadFollowingList() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, isRefreshing = true) }
            followingPaginator.reset()
            followingPaginator.loadNextItems()
            updateState { copy(isLoading = false, isRefreshing = false) }
        }
    }

    override fun onReload() {
        loadFollowingList()
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

    override fun onClickUnfollow(userId: String) {
        tryToCall(
            block = { followRepository.unfollowUser(userId) },
            onSuccess = {
                updateState { copy(followings = followings.filter { it.userId != userId }) }
            },
            onError = { handleError(it) }
        )
    }

    override fun onClickUser(userId: String, userName: String, imageUrl: String?) {
        navigate(StatisticsRoute(userId = userId, userName = userName, userImageUrl = imageUrl))
    }

    override fun onLoadMoreFollowing() {
        viewModelScope.launch {
            updateState { copy(isLoadingMore = true) }
            followingPaginator.loadNextItems()
            updateState { copy(isLoadingMore = false) }
        }
    }
}
