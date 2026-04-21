package com.spendoo.categories.presentation.screen.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject

@Composable
fun CategoriesScreen(
    categoriesViewModel: CategoriesViewModel = koinInject()
) {
    val state by categoriesViewModel.state.collectAsStateWithLifecycle()
    CategoriesScreenContent(state = state, interactionListener = categoriesViewModel)
}

@Composable
private fun CategoriesScreenContent(
    state: CategoriesUiState,
    interactionListener: CategoriesInteractionListener,
) {

}