package com.spendoo.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.home.presentation.screen.components.GoalsSection
import com.spendoo.home.presentation.screen.components.HomeHeader
import com.spendoo.home.presentation.screen.components.OffersSection
import com.spendoo.home.presentation.screen.components.TopSpendingSection
import com.spendoo.home.presentation.screen.components.balanceSection
import org.koin.compose.viewmodel.koinViewModel

//TODO: localization and every data status (loading empty error)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    HomeContent(state = state, viewModel = viewModel)
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    viewModel: HomeInteractionListener
) {
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
                onOfferClicked = viewModel::onOfferClicked
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
                onSpendingClicked = viewModel::onSpendingClicked
            )
        }
    }
}
