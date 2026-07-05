package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources._year
import spendoo.designsystem.generated.resources.billed
import spendoo.designsystem.generated.resources.free
import spendoo.designsystem.generated.resources.ic_done_mark
import spendoo.designsystem.generated.resources.ic_thunder
import spendoo.designsystem.generated.resources.per_month

@Composable
fun SubscriptionBuyCard(
    title: String,
    description: String,
    subscriptionPrice: Double,
    yearlySubscriptionPrice: Double,
    features: List<String>,
    isSelected: Boolean,
    isMostPopular: Boolean,
    modifier: Modifier = Modifier,
    icon: DrawableResource = Res.drawable.ic_thunder,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(16.dp)
) {
    Box {
        Column(
            modifier = modifier
                .background(backgroundColor, shape)
                .border(
                    1.24.dp,
                    if (isSelected) Theme.colorScheme.icon.primary else Theme.colorScheme.border.primary,
                    shape
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                CategoryIcon(
                    icon = icon,
                    iconTint = Theme.colorScheme.icon.primary,
                    size = 40.dp
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = title,
                        style = Theme.typography.title.small,
                        color = Theme.colorScheme.text.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = description,
                        style = Theme.typography.label.medium.small,
                        color = Theme.colorScheme.text.body,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                SubscriptionBuyCardSelectionBadge(isSelected = isSelected)
            }

            when (subscriptionPrice) {
                0.0 -> {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = Res.string.free.asString(),
                        style = Theme.typography.heading.large,
                        color = Theme.colorScheme.text.title,
                    )
                }

                else -> {
                    SubscriptionBuyCardPricing(
                        monthlyPrice = subscriptionPrice,
                        yearlySubscriptionPrice = yearlySubscriptionPrice
                    )
                }
            }

            if (features.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    features.forEach { feature ->
                        SubscriptionBuyCardFeatureRow(feature)
                    }
                }
            }
        }

        if (isMostPopular) {
            Text(
                text = "Most popular",
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.background.secondary,
                modifier = Modifier.align(Alignment.TopCenter)
                    .offset(y = (-10).dp)
                    .background(Theme.colorScheme.gradient.brand, RoundedCornerShape(8.dp))
                    .padding(8.dp, 4.dp)
            )
        }
    }
}

@Composable
private fun SubscriptionBuyCardPricing(
    monthlyPrice: Double,
    yearlySubscriptionPrice: Double,
) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = monthlyPrice.toString(),
            style = Theme.typography.heading.medium,
            color = Theme.colorScheme.text.title,
        )

        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = Res.string.per_month.asString(),
            style = Theme.typography.label.medium.medium,
            color = Theme.colorScheme.text.body,
        )

        if (yearlySubscriptionPrice != 0.0) {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = "${Res.string.billed.asString(yearlySubscriptionPrice)} /${Res.string._year.asString()}",
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.button.primary,
            )
        }
    }
}


@Composable
private fun SubscriptionBuyCardSelectionBadge(isSelected: Boolean) {
    if (isSelected) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Theme.colorScheme.icon.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = Res.drawable.ic_done_mark.painter(),
                contentDescription = null,
                modifier = Modifier.size(10.dp),
                tint = Theme.colorScheme.background.secondary
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(1.dp, Theme.colorScheme.text.body, CircleShape)
        )
    }
}

@Composable
private fun SubscriptionBuyCardFeatureRow(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Theme.colorScheme.button.secondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = Res.drawable.ic_done_mark.painter(),
                contentDescription = null,
                modifier = Modifier.size(10.dp),
                tint = Theme.colorScheme.icon.primary
            )
        }

        Text(
            text = title,
            style = Theme.typography.body.small,
            color = Theme.colorScheme.text.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

private val previewFeatures = listOf(
    "Standard expense tracking",
    "Manual budgeting tools",
    "Up to 3 savings goals",
    "Monthly summary reports",
    "Monthly summary reports",
    "Monthly summary reports",
)


@Composable
@PreviewLightDark
private fun SubscriptionBuyCardPreview() = SpendooTheme {
    SubscriptionBuyCard(
        icon = Res.drawable.ic_thunder,
        title = "Basic",
        description = "The essentials to get your finances on track.",
        subscriptionPrice = 0.0,
        features = previewFeatures,
        isSelected = true,
        yearlySubscriptionPrice = 0.0,
        isMostPopular = false
    )
}


@Composable
@PreviewLightDark
private fun SubscriptionBuyCardPreview2() = SpendooTheme {
    SubscriptionBuyCard(
        icon = Res.drawable.ic_thunder,
        title = "Pro",
        description = "Advanced insights for serious budgeters.",
        subscriptionPrice = 7.99,
        yearlySubscriptionPrice = 95.88,
        features = previewFeatures,
        isSelected = false,
        isMostPopular = true
    )
}
