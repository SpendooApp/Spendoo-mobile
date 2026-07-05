package com.spendoo.categories.presentation.screen.categoryOffers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.cards.RecommendedBrandsCard
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.category_offers
import spendoo.designsystem.generated.resources.no_offers_found

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
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.category_offers),
            onBackClicked = interactionListener::onBackClicked
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.topItemsWithOffers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_offers_found),
                    style = Theme.typography.body.medium,
                    color = Theme.colorScheme.text.body
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.topItemsWithOffers) { item ->
                    RecommendedBrandsCard(
                        categoryName = item.categoryName,
                        usageCount = item.usageCount,
                        totalAmount = item.totalAmount,
                        perEachAmount = item.perEachAmount,
                        offers = item.offers,
                        categoryIcon = item.categoryIcon,
                        onOfferClick = { offer ->
                            offer.offerUrl?.let { url ->
                                if (url.isNotEmpty()) {
                                    uriHandler.openUri(url)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
