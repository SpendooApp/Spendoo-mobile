package com.spendoo.categories.presentation.screen.categoryOffers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryOffersScreen(
    categoryId: String,
    viewModel: CategoryOffersViewModel = koinViewModel(parameters = { parametersOf(categoryId) })
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoryOffersScreenContent(state = state, interactionListener = viewModel)
}

@Composable
private fun CategoryOffersScreenContent(
    state: CategoryOffersUiState,
    interactionListener: CategoryOffersInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "Category Offers",
            onBackClicked = interactionListener::onBackClicked
        )
        
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Offers for category: ${state.categoryId}\n(UI to be implemented)",
                color = Theme.colorScheme.text.body,
                style = Theme.typography.body.medium
            )
        }
    }
}
