package com.spendoo.goals.presentation.screen.achievements

import androidx.lifecycle.viewModelScope
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.goals.domain.entity.Achievement
import com.spendoo.goals.domain.repository.AchievementRepository
import com.spendoo.shared.domain.utils.PageQuery
import kotlinx.coroutines.launch
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.unknown_error

class AchievementsViewModel(
    private val achievementRepository: AchievementRepository
) : BaseViewModel<AchievementsUiState>(AchievementsUiState()), AchievementsInteractionListener {

    private val paginator = createPaginator(
        initialKey = 0,
        pageSize = 20,
        loadPage = { page ->
            achievementRepository.getAchievements(PageQuery(page = page, size = 20)).data
        },
        onSuccess = { items ->
            updateState { copy(achievements = achievements + items) }
        },
        onLoadUpdated = { isLoadingMore ->
            updateState { copy(isLoadingMore = isLoadingMore) }
        },
        onError = { handleError(it) }
    )

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, isRefreshing = true) }
            paginator.reset()
            paginator.loadNextItems()
            updateState { copy(isLoading = false, isRefreshing = false) }
        }
    }

    override fun onReload() {
        loadData()
    }

    override fun onLoadMore() {
        viewModelScope.launch {
            updateState { copy(isLoadingMore = true) }
            paginator.loadNextItems()
            updateState { copy(isLoadingMore = false) }
        }
    }

    private fun handleError(throwable: Throwable?) {
        showSnackBar(
            title = UiText.StringRes(Res.string.an_error_occurred),
            message = throwable?.message?.let(UiText::DynamicString)
                ?: UiText.StringRes(Res.string.unknown_error),
            isSuccess = false
        )
    }

    override fun onBackClicked() {
        popBackStack()
    }

    override fun onAchievementClicked(achievement: Achievement) {
        updateState { copy(selectedAchievement = achievement) }
    }

    override fun onDismissAchievementSheet() {
        updateState { copy(selectedAchievement = null) }
    }
}
