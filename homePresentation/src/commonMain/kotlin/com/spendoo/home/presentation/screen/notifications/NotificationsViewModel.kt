package com.spendoo.home.presentation.screen.notifications

import androidx.lifecycle.viewModelScope
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.notifications.domain.repository.NotificationRepository
import com.spendoo.shared.domain.utils.PageQuery
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.unknown_error

class NotificationsViewModel(
    private val notificationRepository: NotificationRepository
) : BaseViewModel<NotificationsUiState>(NotificationsUiState()), NotificationsInteractionListener {

    private val notificationsPaginator = createPaginator(
        initialKey = 0,
        pageSize = 20,
        loadPage = { page ->
            notificationRepository.getNotifications(PageQuery(page = page, size = 20)).data
        },
        onSuccess = { items ->
            updateState { copy(notifications = notifications + items) }
        },
        onLoadUpdated = { isLoadingMore ->
            updateState { copy(isLoadingMore = isLoadingMore) }
        },
        onError = { handleError(it) }
    )

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, isRefreshing = true) }
            notificationsPaginator.reset()
            notificationsPaginator.loadNextItems()
            updateState { copy(isLoading = false, isRefreshing = false) }
        }
        
        tryToCall(
            block = { notificationRepository.markAllAsRead() },
            onSuccess = { },
            onError = { }
        )
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

    override fun onLoadMoreNotifications() {
        viewModelScope.launch {
            updateState { copy(isLoadingMore = true) }
            notificationsPaginator.loadNextItems()
            updateState { copy(isLoadingMore = false) }
        }
    }

    override fun onReload() {
        loadNotifications()
    }
}
