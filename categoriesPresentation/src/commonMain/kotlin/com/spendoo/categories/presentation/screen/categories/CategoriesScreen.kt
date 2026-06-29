package com.spendoo.categories.presentation.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.AddAmountToCategoryBottomSheet
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.AddEditCategoryBottomSheet
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.CategoryActionsSheet
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.designsystem.utils.pagination.PaginationTrigger
import com.spendoo.designsystem.components.indicator.PullToRefresh
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.cards.CategoryCard
import com.spendoo.designsystem.components.cards.MoneyCard
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.color.scheme.toBrush
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.added_income
import spendoo.designsystem.generated.resources.categories
import spendoo.designsystem.generated.resources.ic_plus
import spendoo.designsystem.generated.resources.no_categories_yet
import spendoo.designsystem.generated.resources.total_budget
import spendoo.designsystem.generated.resources.total_spending

@Composable
fun CategoriesScreen(
    categoriesViewModel: CategoriesViewModel = koinViewModel()
) {
    val state by categoriesViewModel.state.collectAsStateWithLifecycle()

    PullToRefresh(
        isRefreshing = state.isRefreshing,
        onRefresh = categoriesViewModel::onReload
    ) {
        CategoriesScreenContent(state = state, interactionListener = categoriesViewModel)
    }
}

@Composable
private fun CategoriesScreenContent(
    state: CategoriesUiState,
    interactionListener: CategoriesInteractionListener,
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
            title = stringResource(Res.string.categories),
            actions = listOf(
                {
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_plus,
                        contentDescription = stringResource(Res.string.categories),
                        onClick = { interactionListener.onAddCategoryClicked() }
                    )
                }
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MoneyCard(
                        modifier = Modifier.fillMaxWidth(),
                        isLoading = state.isSummaryLoading,
                        amount = state.summary.totalBudget.toInt().toString(),
                        amountColor = Theme.colorScheme.brand.onPrimary,
                        amountTextStyle = Theme.typography.heading.large,
                        title = stringResource(Res.string.total_budget),
                        titleColor = Theme.colorScheme.brand.primaryVariant,
                        titleTextStyle = Theme.typography.body.small,
                        backgroundColor = Theme.colorScheme.gradient.brand,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MoneyCard(
                            modifier = Modifier.weight(1f),
                            isLoading = state.isSummaryLoading,
                            amount = state.summary.totalSpent.toInt().toString(),
                            amountColor = Theme.colorScheme.brand.onSecondary,
                            amountTextStyle = Theme.typography.heading.small,
                            title = stringResource(Res.string.total_spending),
                            titleColor = Theme.colorScheme.text.body,
                            titleTextStyle = Theme.typography.label.medium.small,
                            backgroundColor = Theme.colorScheme.brand.secondary.toBrush(),
                            borderColor = Theme.colorScheme.border.primary
                        )
                        MoneyCard(
                            modifier = Modifier.weight(1f),
                            isLoading = state.isSummaryLoading,
                            amount = state.summary.addedIncome.toInt().toString(),
                            amountColor = Theme.colorScheme.additional.onSuccess,
                            amountTextStyle = Theme.typography.heading.small,
                            title = stringResource(Res.string.added_income),
                            titleColor = Theme.colorScheme.text.body,
                            titleTextStyle = Theme.typography.label.medium.small,
                            backgroundColor = Theme.colorScheme.brand.secondary.toBrush(),
                            borderColor = Theme.colorScheme.border.primary
                        )
                    }
                }
            }

            if (state.isCategoriesLoading) {
                items(5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .shimmerEffect()
                    )
                }
            } else if (state.categories.isNotEmpty()) {
                items(state.categories) { category ->
                    CategoryCard(
                        modifier = Modifier.fillMaxWidth(),
                        icon = category.categoryIcon.toDrawableResource(),
                        title = category.categoryName,
                        current = category.budget.spentAmount.toInt(),
                        budgetData = if (category.budget.amount > 0) {
                            category.budget.toBudgetDataUiState()
                        } else null,
                        onClickMenu = { interactionListener.setCategoryToEdit(category) }
                    )
                }
            }

            if (state.isCategoriesLoadingMore) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
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
        if (!state.isCategoriesLoading && state.categories.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_categories_yet),
                    style = Theme.typography.body.medium,
                    color = Theme.colorScheme.text.body,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    AddEditCategoryBottomSheet(
        isVisible = state.isAddCategoryBottomSheetVisible,
        onDismiss = interactionListener::onAddCategoryBottomSheetDismissed,
        initialAddEditCategoryUiState = state.categoryToEdit,
        onAddCategory = interactionListener::onAddEditCategory,
    )
    CategoryActionsSheet(
        show = state.isCategoryActionsSheetVisible,
        onOptionSelected = interactionListener::onCategoryActionSelected,
        onDismiss = interactionListener::onCategoryActionsSheetDismissed
    )

    AddAmountToCategoryBottomSheet(
        isVisible = state.isAddAmountToCategoryVisible,
        onDismiss = interactionListener::onDismissAddAmountToCategory,
        categoryName = state.categoryToEdit?.categoryName ?: "",
        initialAmount = state.summary.addedIncome.takeIf { it > 0 },
        onAddAmount = { amount ->
            interactionListener.onAddAmountToCategory(amount)
        },
        isLoading = state.isAddAmountToCategoryLoading
    )
}