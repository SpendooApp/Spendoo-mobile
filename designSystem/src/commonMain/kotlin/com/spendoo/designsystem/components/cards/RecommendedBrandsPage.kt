package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_drink
import spendoo.designsystem.generated.resources.ic_money_in_offer
import spendoo.designsystem.generated.resources.ic_thunder
import spendoo.designsystem.generated.resources.no_offers_yet
import spendoo.designsystem.generated.resources.price_each
import spendoo.designsystem.generated.resources.recommended_brands
import spendoo.designsystem.generated.resources.times_per_month

@Composable
fun RecommendedBrandsCard(
    categoryName: String,
    usageCount: Int,
    totalAmount: String,
    perEachAmount: String,
    offers: List<OfferItemCardUiState>,
    modifier: Modifier = Modifier,
    categoryIcon: DrawableResource,
    onOfferClick: ((OfferItemCardUiState) -> Unit)? = null,
    shape: Shape = RoundedCornerShape(16.dp)
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Theme.colorScheme.background.secondary)
            .border(1.dp, Theme.colorScheme.border.primary, shape)
            .padding(16.dp),
    ) {
        CardHeader(
            modifier = Modifier.padding(bottom = 16.dp),
            categoryIcon = categoryIcon,
            categoryName = categoryName,
            usageCount = usageCount,
            totalAmount = totalAmount,
            perEachAmount = perEachAmount
        )

        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = Res.drawable.ic_thunder.painter(),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Theme.colorScheme.additional.onWarning
            )
            Text(
                text = stringResource(Res.string.recommended_brands),
                style = Theme.typography.label.medium.medium,
                color = Theme.colorScheme.text.title
            )
        }

        if (offers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_offers_yet),
                    style = Theme.typography.label.medium.medium,
                    color = Theme.colorScheme.text.titleSmall
                )
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                offers.forEach { offer ->
                    OfferItemCard(
                        offer = offer,
                        onClick = if (onOfferClick != null) { { onOfferClick(offer) } } else null
                    )
                }
            }
        }
    }


    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun CardHeader(
    categoryIcon: DrawableResource,
    categoryName: String,
    usageCount: Int,
    totalAmount: String,
    perEachAmount: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CategoryIcon(
            icon = categoryIcon,
            size = 40.dp
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = categoryName,
                style = Theme.typography.title.small,
                color = Theme.colorScheme.text.title
            )
            Text(
                text = stringResource(Res.string.times_per_month, usageCount),
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.text.body
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = Res.drawable.ic_money_in_offer.painter(),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Theme.colorScheme.text.title
                )
                Text(
                    text = totalAmount,
                    style = Theme.typography.label.medium.large,
                    color = Theme.colorScheme.text.title
                )
            }
            Text(
                text = stringResource(Res.string.price_each, perEachAmount),
                style = Theme.typography.label.medium.extraSmall,
                color = Theme.colorScheme.text.body
            )
        }
    }
}


@PreviewLightDark
@Composable
fun RecommendedBrandsCardPreview() = SpendooTheme {
    RecommendedBrandsCard(
        categoryName = "Coffee",
        usageCount = 24,
        totalAmount = "8,400",
        perEachAmount = "350",
        offers = listOf(
            OfferItemCardUiState(
                title = "Nescafe Gold",
                description = "Premium instant coffee - Make it at home",
                save = -4800,
                previous = 350,
                after = 150,
            ),
            OfferItemCardUiState(
                title = "Cafe Coffee Day",
                description = "Local chain with similar quality",
                save = -4080,
                previous = 350,
                after = 180,
            ),
            OfferItemCardUiState(
                title = "Blue Tokai Coffee",
                description = "Specialty coffee beans for home brewing",
                save = -3600,
                previous = 350,
                after = 200,
            ),
        ),
        categoryIcon = Res.drawable.ic_drink
    )
}