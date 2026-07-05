package com.spendoo.home.presentation.screen

import com.spendoo.categories.domain.repository.CategoriesRepository
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.toUiText
import com.spendoo.goals.api.GoalsRoute
import com.spendoo.goals.domain.repository.GoalsRepository
import com.spendoo.identity.domain.repository.ProfileRepository
import com.spendoo.offers.domain.repository.OffersRepository
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.error_loading_balance_summary
import spendoo.designsystem.generated.resources.error_loading_goals
import spendoo.designsystem.generated.resources.error_loading_offers
import spendoo.designsystem.generated.resources.error_loading_top_spending
import spendoo.designsystem.generated.resources.error_loading_user_data
import com.spendoo.shared.domain.utils.PageQuery

class HomeViewModel(
    private val offersRepository: OffersRepository,
    private val transactionsRepository: TransactionsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val profileRepository: ProfileRepository,
    private val goalsRepository: GoalsRepository
) : BaseViewModel<HomeUiState>(HomeUiState()), HomeInteractionListener {

    init {
        listenToResetSignal()
        getHomeData()
    }

    private fun listenToResetSignal() {
        tryToCollect(
            block = {
                getResult<Boolean?>("reset", consume = true)
            },
            onEach = { shouldReset ->
                if (shouldReset == true) {
                    getHomeData()
                }
            },
            onError = {}
        )
    }

    private fun getHomeData() {
        loadBalanceSummary()
        loadUserProfile()
        loadNotificationsCount()
        loadOffers()
        loadGoals()
        loadTopSpending()
    }

    private fun loadBalanceSummary() {
        tryToCall(
            block = { transactionsRepository.getBalanceSummary() },
            onStart = { updateState { copy(isBalanceLoading = true) } },
            onSuccess = { summary ->
                updateState { copy(balanceSummary = summary.toUiState()) }
            },
            onError = { error ->
                error.message?.let {
                    showSnackBar(
                        title = Res.string.error_loading_balance_summary.toUiText(),
                        message = UiText.DynamicString(it),
                        isSuccess = false,
                    )
                }
            },
            onEnd = {
                updateState { copy(isBalanceLoading = false) }
                checkRefreshFinished()
            }
        )
    }

    private fun loadUserProfile() {
        tryToCall(
            block = { profileRepository.getProfile() },
            onStart = { updateState { copy(isUserLoading = true) } },
            onSuccess = { profile ->
                updateState {
                    copy(
                        userData = it.userData.copy(
                            userName = profile.fullName,
                            userImageUrl = profile.imageUrl
                        )
                    )
                }
            },
            onError = { error ->
                error.message?.let {
                    showSnackBar(
                        title = Res.string.error_loading_user_data.toUiText(),
                        message = UiText.DynamicString(it),
                        isSuccess = false,
                    )
                }
            },
            onEnd = {
                updateState { copy(isUserLoading = false) }
                checkRefreshFinished()
            }
        )
    }

    private fun loadNotificationsCount() {
        tryToCall(
            block = { profileRepository.getNotificationsCount() },
            onStart = { updateState { copy(isNotificationsLoading = true) } },
            onSuccess = { count ->
                updateState {
                    copy(
                        userData = it.userData.copy(
                            notificationsCount = count
                        )
                    )
                }
            },
            onError = { },
            onEnd = {
                updateState { copy(isNotificationsLoading = false) }
                checkRefreshFinished()
            }
        )
    }

    private fun loadOffers() {
        tryToCall(
            block = { offersRepository.getOffers(PageQuery(0, 20)) },
            onStart = { updateState { copy(isOffersLoading = true) } },
            onSuccess = { offers ->
                updateState { copy(offers = offers.data.map { it.toUiState() }) }
            },
            onError = { error ->
                error.message?.let {
                    showSnackBar(
                        title = Res.string.error_loading_offers.toUiText(),
                        message = UiText.DynamicString(it),
                        isSuccess = false,
                    )
                }
            },
            onEnd = {
                updateState { copy(isOffersLoading = false) }
                checkRefreshFinished()
            }
        )
    }

    private fun loadGoals() {
        tryToCall(
            block = { goalsRepository.getGoals(PageQuery(0, 20)) },
            onStart = { updateState { copy(isGoalsLoading = true) } },
            onSuccess = { goals ->
                updateState { copy(goals = goals.data.map { it.toUiState() }) }
            },
            onError = { error ->
                error.message?.let {
                    showSnackBar(
                        title = Res.string.error_loading_goals.toUiText(),
                        message = UiText.DynamicString(it),
                        isSuccess = false,
                    )
                }
            },
            onEnd = {
                updateState { copy(isGoalsLoading = false) }
                checkRefreshFinished()
            }
        )
    }

    private fun loadTopSpending() {
        tryToCall(
            block = {
                categoriesRepository.getTopSpending(
                    PageQuery(page = 0, size = 5)
                )
            },
            onStart = { updateState { copy(isTopSpendingLoading = true) } },
            onSuccess = { spending ->
                updateState { copy(topSpending = spending.data.map { it.toUiState() }) }
            },
            onError = { error ->
                error.message?.let {
                    showSnackBar(
                        title = Res.string.error_loading_top_spending.toUiText(),
                        message = UiText.DynamicString(it),
                        isSuccess = false,
                    )
                }
            },
            onEnd = {
                updateState { copy(isTopSpendingLoading = false) }
                checkRefreshFinished()
            }
        )
    }

    private fun checkRefreshFinished() {
        updateState {
            if (!isBalanceLoading && !isUserLoading && !isNotificationsLoading && !isOffersLoading && !isGoalsLoading && !isTopSpendingLoading) {
                copy(isRefreshing = false)
            } else {
                this
            }
        }
    }

    override fun onReload() {
        updateState { copy(isRefreshing = true) }
        getHomeData()
    }

    override fun onOfferClicked(offerId: String) {}
    override fun onGoalClicked(goalId: String) {}
    override fun onSpendingClicked(spendingId: String) {}
    override fun onViewAllOffersClicked() {}
    override fun onViewAllGoalsClicked() {
        navigate(GoalsRoute)
    }

    override fun onViewAllSpendingClicked() {}
    override fun onNotificationClicked() {}
    override fun onProfileClicked() {}
}
