package com.spendoo.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.home.presentation.screen.components.GoalsSection
import com.spendoo.home.presentation.screen.components.HomeHeader
import com.spendoo.home.presentation.screen.components.OffersSection
import com.spendoo.home.presentation.screen.components.TopSpendingSection
import com.spendoo.home.presentation.screen.components.balanceSection
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PullToRefresh(
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::onReload
    ) {
        HomeContent(state = state, viewModel = viewModel)
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    viewModel: HomeInteractionListener,
) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .navigationBarsPadding()
            .statusBarsPadding(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stickyHeader {
            HomeHeader(
                userName = state.userData.userName,
                userImageUrl = state.userData.userImageUrl,
                notificationsCount = state.userData.notificationsCount,
                isUserLoading = state.isUserLoading,
                isNotificationsLoading = state.isNotificationsLoading,
                onNotificationClicked = viewModel::onNotificationClicked,
                onProfileClicked = viewModel::onProfileClicked
            )
        }

        balanceSection(
            balanceSummary = state.balanceSummary,
            isLoading = state.isBalanceLoading
        )

        item {
            OffersSection(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                offers = state.offers,
                isLoading = state.isOffersLoading,
                onViewAll = viewModel::onViewAllOffersClicked,
                onOfferClicked = { link ->
                    link?.takeIf { it.isNotBlank() }?.let { url ->
                        val formattedUrl = if (url.startsWith("http://") || url.startsWith("https://")) url else "https://$url" //TODO: format url function
                        uriHandler.openUri(formattedUrl)
                    }
                    viewModel.onOfferClicked(link)
                }
            )
        }

        item {
            GoalsSection(
                goals = state.goals,
                isLoading = state.isGoalsLoading,
                onViewAll = viewModel::onViewAllGoalsClicked,
                onGoalClicked = viewModel::onGoalClicked
            )
        }

        item {
            TopSpendingSection(
                spending = state.topSpending,
                isLoading = state.isTopSpendingLoading,
                onViewAll = viewModel::onViewAllSpendingClicked,
                onCategoryClicked = viewModel::onSpendingClicked
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp).navigationBarsPadding())
        }
    }
}
