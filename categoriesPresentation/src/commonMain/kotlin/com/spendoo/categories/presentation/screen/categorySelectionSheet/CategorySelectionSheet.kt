package com.spendoo.categories.presentation.screen.categorySelectionSheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.AddEditCategoryBottomSheet
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.CategoryItemUiState
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components.CategoryItem
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components.CategoryItemShimmer
import com.spendoo.categories.presentation.shared.pagination.PaginationTrigger
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.SpendooPreview
import com.spendoo.designsystem.utils.extentions.asString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add_category
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.choose_your_category
import spendoo.designsystem.generated.resources.ic_plus
import spendoo.designsystem.generated.resources.no_categories_yet
import spendoo.designsystem.generated.resources.select

@Composable
fun CategorySelectionSheet(
    isVisible: Boolean,
    onCategorySelected: (CategoryItemUiState) -> Unit,
    onDismiss: () -> Unit,
    viewModel: CategorySelectionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(isVisible) {
        if (isVisible) {
            viewModel.loadCategories()
        }
    }

    CategorySelectionSheetMainContent(
        isVisible = isVisible,
        state = state,
        interactionListener = viewModel,
        onDismiss = onDismiss,
        onCategorySelected = onCategorySelected
    )
}

@Composable
private fun CategorySelectionSheetMainContent(
    isVisible: Boolean,
    state: CategorySelectionUiState,
    interactionListener: CategorySelectionInteractionListener,
    onDismiss: () -> Unit,
    onCategorySelected: (CategoryItemUiState) -> Unit
) {
    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        horizontalPadding = 0.dp
    ) {
        CategorySelectionContent(
            state = state,
            onCategorySelected = interactionListener::onCategorySelected,
            onDismiss = onDismiss,
            onConfirm = { state.selectedCategory?.let { onCategorySelected(it) } },
            onAddCategoryClicked = { interactionListener.showAddCategorySheet(true) },
            onListScrolled = interactionListener::onListScrolled,
        )
    }

    AddEditCategoryBottomSheet(
        isVisible = state.showAddCategorySheet,
        onDismiss = { interactionListener.showAddCategorySheet(false) },
        initialAddEditCategoryUiState = null,
        onAddCategory = {
            interactionListener.showAddCategorySheet(false)
            interactionListener.loadCategories()
        }
    )
}

@Composable
private fun CategorySelectionContent(
    state: CategorySelectionUiState,
    onCategorySelected: (CategoryItemUiState) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onAddCategoryClicked: () -> Unit,
    onListScrolled: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheetTemplate(
        title = Res.string.choose_your_category.asString(),
        trailingContent = {
            SpendooIconButton(
                onClick = onAddCategoryClicked,
                iconRes = Res.drawable.ic_plus,
                tint = Theme.colorScheme.button.primary,
                iconSize = 24.dp,
                size = 48.dp,
                showBorder = false,
                backgroundColor = Theme.colorScheme.button.secondary,
                contentDescription = Res.string.add_category.asString()
            )
        },
        dismissText = Res.string.cancel.asString(),
        onDismiss = onDismiss,
        onClickAction = onConfirm,
        actionText = Res.string.select.asString(),
        actionButtonState = if (state.selectedCategory != null) AppButtonState.Enabled
        else AppButtonState.Disabled,
        modifier = modifier
    ) { listState ->

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        if (state.isLoading) {
            items(3) {
                CategoryItemShimmer()
            }
        } else if (state.categories.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.no_categories_yet),
                        style = Theme.typography.label.medium.medium,
                        color = Theme.colorScheme.text.label
                    )
                }
            }
        } else {
            items(state.categories) { category ->
                CategoryItem(
                    category = category,
                    isSelected = state.selectedCategory?.id == category.id,
                    onClick = { onCategorySelected(category) }
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
            PaginationTrigger(
                list = state.categories,
                listState = listState,
                remainingItemsToLoadNextPage = 5,
                loadNextItems = onListScrolled
            )
        }
    }

}


@Composable
@Preview
fun CategorySelectionSheetPreview() = SpendooPreview {
    var selectedCategory by remember { mutableStateOf<CategoryItemUiState?>(null) }

    CategorySelectionContent(
        state = CategorySelectionUiState(
            categories = listOf(
                CategoryItemUiState("1", "Food", CategoryIcon.FOOD),
                CategoryItemUiState("2", "Transport", CategoryIcon.TRANSPORT),
                CategoryItemUiState("3", "Entertainment", CategoryIcon.ENTERTAINMENT)
            ),
            isLoading = true,
            selectedCategory = selectedCategory
        ),
        onCategorySelected = { selectedCategory = it },
        onDismiss = {},
        onConfirm = {},
        onAddCategoryClicked = {},
        onListScrolled = {}
    )
}