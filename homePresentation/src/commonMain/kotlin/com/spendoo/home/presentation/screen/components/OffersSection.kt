package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.formatMoney
import com.spendoo.home.presentation.screen.OfferUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.no_offers_yet
import spendoo.designsystem.generated.resources.offer_image
import spendoo.designsystem.generated.resources.offers

@Composable
fun OffersSection(
    modifier: Modifier = Modifier,
    offers: List<OfferUiState>,
    isLoading: Boolean,
    onViewAll: () -> Unit,
    onOfferClicked: (String?) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SectionHeader(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(Res.string.offers),
            onViewAll = onViewAll
        )
        if (isLoading) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(3) {
                    Box(
                        modifier = Modifier
                            .width(260.dp)
                            .height(170.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .shimmerEffect()
                    )
                }
            }
        } else if (offers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_offers_yet),
                    style = Theme.typography.label.medium.medium,
                    color = Theme.colorScheme.text.titleSmall
                )
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(offers) { offer ->
                    OfferCard(offer = offer, onClick = { onOfferClicked(offer.link) })
                }
            }
        }
    }
}

@Composable
private fun OfferCard(offer: OfferUiState, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White)
            .clickableNoRipple { onClick() }
    ) {
        AsyncImage(
            model = offer.imageUrl,
            contentDescription = stringResource(Res.string.offer_image),
            contentScale = ContentScale.Fit,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        offer.discountPercent?.let { discount ->
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(Theme.colorScheme.additional.error, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "-$discount%",
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.additional.onError
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            offer.title?.let { title ->
                Text(
                    text = title,
                    style = Theme.typography.body.medium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val priceText = if (offer.price != null) {
                    val formattedPrice = formatMoney(offer.price)
                    "$formattedPrice ${offer.currency.orEmpty()}"
                } else null

                priceText?.let { price ->
                    Text(
                        text = price,
                        style = Theme.typography.label.medium.medium,
                        color = Color.White
                    )
                }

                offer.rating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "★",
                            color = Theme.colorScheme.additional.golden,
                            style = Theme.typography.label.medium.medium
                        )
                        Text(
                            text = rating.toString(),
                            style = Theme.typography.label.medium.medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
