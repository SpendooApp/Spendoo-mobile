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
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.error_loading_offers
import spendoo.designsystem.generated.resources.error_loading_user_data
import com.spendoo.categories.domain.utils.PageQuery as CategoriesPageQuery
import com.spendoo.goals.domain.utils.PageQuery as GoalsPageQuery // TODO: add comon domain classes in shared domain module
import com.spendoo.offers.domain.utils.PageQuery as OffersPageQuery

class HomeViewModel(
    private val offersRepository: OffersRepository,
    private val transactionsRepository: TransactionsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val profileRepository: ProfileRepository,
    private val goalsRepository: GoalsRepository
) : BaseViewModel<HomeUiState>(HomeUiState()), HomeInteractionListener {

    init {
        listenToResetSignal()
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
        loadHeaderData()
        loadContentData()
    }

    private fun loadHeaderData() {
        tryToCall(
            block = {
                coroutineScope {
                    val balanceDeferred = async { transactionsRepository.getBalanceSummary() }
                    val profileDeferred = async { profileRepository.getProfile() }
                    val notificationsDeferred = async { profileRepository.getNotificationsCount() }
                    Triple(
                        balanceDeferred.await(),
                        profileDeferred.await(),
                        notificationsDeferred.await()
                    )
                }
            },
            onStart = {
                updateState {
                    copy(
                        isBalanceLoading = true,
                        isUserLoading = true,
                        isNotificationsLoading = true
                    )
                }
            },
            onSuccess = { (summary, profile, notificationsCount) ->
                updateState {
                    copy(
                        balanceSummary = summary.toUiState(),
                        userData = it.userData.copy(
                            userName = profile.fullName,
                            userImageUrl = profile.imageUrl,
                            notificationsCount = notificationsCount
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
                updateState {
                    copy(
                        isBalanceLoading = false,
                        isUserLoading = false,
                        isNotificationsLoading = false
                    )
                }
            }
        )
    }

    private fun loadContentData() {
        tryToCall(
            block = {
                coroutineScope {
                    val offersDeferred =
                        async { offersRepository.getOffers(OffersPageQuery(0, 20)) }
                    val goalsDeferred = async { goalsRepository.getGoals(GoalsPageQuery(0, 20)) }
                    val spendingDeferred = async {
                        categoriesRepository.getTopSpending(
                            CategoriesPageQuery(
                                page = 0,
                                size = 5
                            )
                        )
                    }
                    Triple(offersDeferred.await(), goalsDeferred.await(), spendingDeferred.await())
                }
            },
            onStart = {
                updateState {
                    copy(
                        isOffersLoading = true,
                        isGoalsLoading = true,
                        isTopSpendingLoading = true
                    )
                }
            },
            onSuccess = { (offers, goals, spending) ->
                updateState {
                    copy(
                        offers = offers.data.map { it.toUiState() },
                        goals = goals.data.map { it.toUiState() },
                        topSpending = spending.data.map { it.toUiState() }
                    )
                }
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
                updateState {
                    copy(
                        isOffersLoading = false,
                        isGoalsLoading = false,
                        isTopSpendingLoading = false
                    )
                }
            }
        )
    }

    override fun onReload() {
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
