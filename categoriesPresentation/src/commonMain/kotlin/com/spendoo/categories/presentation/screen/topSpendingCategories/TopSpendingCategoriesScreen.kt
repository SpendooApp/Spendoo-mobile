package com.spendoo.categories.presentation.screen.topSpendingCategories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.cards.TopSpendingItem
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.top_spending_categories
import kotlin.math.absoluteValue

@Composable
fun TopSpendingCategoriesScreen(
    viewModel: TopSpendingCategoriesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PullToRefresh(
        isRefreshing = state.isRefreshing,
        onRefresh = viewModel::onReload
    ) {
        TopSpendingCategoriesScreenContent(state = state, interactionListener = viewModel)
    }
}

@Composable
private fun TopSpendingCategoriesScreenContent(
    state: TopSpendingCategoriesUiState,
    interactionListener: TopSpendingCategoriesInteractionListener
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = Res.string.top_spending_categories.asString(),
            onBackClicked = interactionListener::onBackClicked
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.isLoading) {
                items(5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .shimmerEffect()
                    )
                }
            } else {
                items(state.categories) { category ->
                    TopSpendingItem(
                        icon = category.categoryIcon.toDrawableResource(),
                        categoryName = category.categoryName,
                        amount = category.totalAmount.absoluteValue.toString(),
                        onClick = { interactionListener.onCategoryClicked(category.categoryId) }
                    )
                }
            }

            if (state.isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp).navigationBarsPadding())
            }
        }

        PaginationTrigger(
            list = state.categories,
            listState = listState,
            remainingItemsToLoadNextPage = 5,
            loadNextItems = interactionListener::onListScrolled
        )
    }
}
