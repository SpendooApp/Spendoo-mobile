package com.spendoo.categories.presentation.screen.categoryOffers

import com.spendoo.designsystem.components.cards.OfferItemCardUiState
import org.jetbrains.compose.resources.DrawableResource

data class CategoryOffersItemUiState(
    val categoryName: String,
    val usageCount: Int,
    val totalAmount: String,
    val perEachAmount: String,
    val categoryIcon: DrawableResource,
    val offers: List<OfferItemCardUiState>
)

data class CategoryOffersUiState(
    val categoryId: String? = null,
    val categoryName: String = "",
    val isLoading: Boolean = false,
    val topItemsWithOffers: List<CategoryOffersItemUiState> = emptyList()
)
