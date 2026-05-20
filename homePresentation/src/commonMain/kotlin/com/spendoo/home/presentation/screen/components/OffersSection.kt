package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.home.presentation.screen.OfferUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.offers
import spendoo.designsystem.generated.resources.offer_image
import spendoo.designsystem.generated.resources.offer_off

@Composable
fun OffersSection(
    modifier: Modifier = Modifier,
    offers: List<OfferUiState>,
    isLoading: Boolean,
    onViewAll: () -> Unit,
    onOfferClicked: (String) -> Unit
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
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(offers) { offer ->
                    OfferCard(offer = offer, onClick = { onOfferClicked(offer.id) })
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
            .background(Color.LightGray)
            .clickableNoRipple { onClick() }
    ) {
        AsyncImage(
            model = offer.imageUrl,
            contentDescription = stringResource(Res.string.offer_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        offer.discountPercent?.let { discount ->
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(115.dp)
                    .background(Theme.colorScheme.background.octonary.copy(alpha = 0.7f))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$discount %",
                        style = Theme.typography.heading.large,
                        color = Theme.colorScheme.border.primary
                    )
                    Text(
                        text = stringResource(Res.string.offer_off),
                        style = Theme.typography.label.medium.medium,
                        color = Theme.colorScheme.border.primary
                    )
                }
            }
        }
    }
}
