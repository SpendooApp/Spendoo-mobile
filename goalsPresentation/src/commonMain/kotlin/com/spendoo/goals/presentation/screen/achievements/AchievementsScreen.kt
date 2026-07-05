package com.spendoo.goals.presentation.screen.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.AchievementBottomSheet
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import com.spendoo.goals.presentation.screen.achievements.components.AchievementCard
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.achievements

@Composable
fun AchievementsScreen(
    viewModel: AchievementsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AchievementsContent(
        state = state,
        listener = viewModel
    )
}

@Composable
fun AchievementsContent(
    state: AchievementsUiState,
    listener: AchievementsInteractionListener
) {
    val gridState = rememberLazyGridState()

    PaginationTrigger(
        list = state.achievements,
        listState = gridState,
        remainingItemsToLoadNextPage = 5,
        loadNextItems = listener::onLoadMore
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                title = stringResource(Res.string.achievements),
                onBackClicked = listener::onBackClicked
            )
            
            PullToRefresh(
                isRefreshing = state.isRefreshing,
                onRefresh = listener::onReload
            ) {
                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = state.achievements,
                        key = { "ach_${it.id}" }
                    ) { achievement ->
                        AchievementCard(
                            icon = achievement.type.toImage(),
                            achievement = achievement,
                            onClick = { listener.onAchievementClicked(achievement) }
                        )
                    }
                }
            }
        }
    }

    state.selectedAchievement?.let { achievement ->
        AchievementBottomSheet(
            isVisible = true,
            onDismiss = listener::onDismissAchievementSheet,
            level = achievement.level,
            icon = achievement.type.toImage(),
            type = achievement.type.toName().asString(),
            name = achievement.title,
            isUnlocked = achievement.isUnlocked,
            description = achievement.description
        )
    }
}
