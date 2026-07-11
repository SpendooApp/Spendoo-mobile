package com.spendoo.categories.presentation.screen.categoryOffers

import com.spendoo.categories.domain.entity.transaction.FrequencyItem
import com.spendoo.categories.domain.repository.TransactionsRepository
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.designsystem.components.cards.OfferItemCardUiState
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.offers.domain.repository.OffersRepository
import com.spendoo.shared.domain.utils.PageQuery
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import kotlin.math.absoluteValue

class CategoryOffersViewModel(
    private val categoryId: String?,
    private val transactionsRepository: TransactionsRepository,
    private val offersRepository: OffersRepository
) : BaseViewModel<CategoryOffersUiState>(CategoryOffersUiState(categoryId = categoryId)),
    CategoryOffersInteractionListener {

    init {
        loadCategoryOffers()
    }

    private fun loadCategoryOffers() {
        updateState { it.copy(isLoading = true) }
        tryToCall(
            block = {
                transactionsRepository.getTopFrequencyItems(
                    categoryId = categoryId?.ifEmpty { null },
                    pageQuery = PageQuery(page = 0, size = 3)
                )
            },
            onSuccess = { frequencyData ->
                val items = frequencyData.data
                if (items.isEmpty()) {
                    updateState { it.copy(isLoading = false, topItemsWithOffers = emptyList()) }
                    return@tryToCall
                }

                loadOffersForItems(items)
            },
            onError = { throwable ->
                updateState { it.copy(isLoading = false) }
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }

    private fun loadOffersForItems(items: List<FrequencyItem>) {
        tryToCall(
            block = {
                items.map { item ->
                    val offersResult = try {
                        offersRepository.getOffers(
                            query = PageQuery(page = 0, size = 3),
                            keywords = listOf(item.itemName)
                        ).data
                    } catch (_: Exception) {
                        emptyList()
                    }

                    val eachAmount = item.averageAmount.absoluteValue

                    val offerCards = offersResult.map { offer ->
                        val price = offer.price ?: 0.0
                        val saveAmount = (eachAmount - price).toInt()
                        OfferItemCardUiState(
                            title = offer.title ?: item.itemName,
                            description = offer.title ?: item.itemName,
                            save = if (saveAmount != 0) -saveAmount else 0,
                            previous = eachAmount.toInt(),
                            after = price.toInt(),
                            offerUrl = offer.link
                        )
                    }

                    CategoryOffersItemUiState(
                        categoryName = item.itemName,
                        usageCount = item.frequency.toInt(),
                        totalAmount = formatAmount(item.totalAmount.absoluteValue),
                        perEachAmount = formatAmount(eachAmount.absoluteValue),
                        categoryIcon = item.categoryIcon.toDrawableResource(),
                        offers = offerCards
                    )
                }
            },
            onSuccess = { uiItems ->
                updateState { it.copy(topItemsWithOffers = uiItems, isLoading = false) }
            },
            onError = { throwable ->
                updateState { it.copy(isLoading = false) }
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = throwable.message?.let { UiText.DynamicString(it) },
                    isSuccess = false
                )
            }
        )
    }


    private fun formatAmount(amount: Double): String {
        return amount.toInt().toString()
    }

    override fun onBackClicked() {
        popBackStack()
    }

}
