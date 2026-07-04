package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.billed
import spendoo.designsystem.generated.resources.free
import spendoo.designsystem.generated.resources.ic_done_mark
import spendoo.designsystem.generated.resources.ic_thunder
import spendoo.designsystem.generated.resources.per_month
import spendoo.designsystem.generated.resources.year

@Composable
fun SubscriptionBuyCard(
    icon: DrawableResource,
    title: String,
    description: String,
    type: String? = null,
    subscriptionPrice: String? = null,
    yearlySubscriptionPrice: String? = null,
    features: List<String> = emptyList(),
    isSelected: Boolean = true,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(24.dp)
) {
    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.24.dp, if (isSelected) Theme.colorScheme.border.active else Theme.colorScheme.border.primary, shape)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            SubscriptionBuyCardIcon(icon = icon)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = Theme.typography.title.large,
                    color = Theme.colorScheme.text.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = description,
                    style = Theme.typography.body.small,
                    color = Theme.colorScheme.text.body,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (isSelected) {
                SubscriptionBuyCardSelectionBadge()
            } else {
                Spacer(modifier = Modifier.size(32.dp))
            }
        }

        when {
            !type.isNullOrBlank() -> {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = type,
                    style = Theme.typography.heading.large,
                    color = Theme.colorScheme.text.title,
                )
            }

            !subscriptionPrice.isNullOrBlank() -> {
                SubscriptionBuyCardPricing(
                    monthlyPrice = subscriptionPrice,
                    yearlySubscriptionPrice = yearlySubscriptionPrice
                )
            }

            else -> {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = Res.string.free.asString(),
                    style = Theme.typography.heading.large,
                    color = Theme.colorScheme.text.title,
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
}

@Composable
private fun SubscriptionBuyCardPricing(
    monthlyPrice: String,
    yearlySubscriptionPrice: String?,
) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = monthlyPrice,
            style = Theme.typography.heading.large,
            color = Theme.colorScheme.text.title,
        )

        Text(
            modifier = Modifier.padding(start = 2.dp, bottom = 2.dp),
            text = Res.string.per_month.asString(),
            style = Theme.typography.body.small,
            color = Theme.colorScheme.text.body,
        )

        if (!yearlySubscriptionPrice.isNullOrBlank()) {
            Text(
                modifier = Modifier.padding(start = 10.dp, bottom = 2.dp),
                text = "${Res.string.billed.asString(yearlySubscriptionPrice)} /${Res.string.year.asString().lowercase()}",
                style = Theme.typography.body.small,
                color = Theme.colorScheme.button.primary,
            )
        }
    }
}


@Composable
private fun SubscriptionBuyCardIcon(icon: DrawableResource) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(Theme.colorScheme.button.secondary, RoundedCornerShape(18.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon.painter(),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = Theme.colorScheme.icon.primary
        )
    }
}

@Composable
private fun SubscriptionBuyCardSelectionBadge() {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Theme.colorScheme.icon.primary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = Res.drawable.ic_done_mark.painter(),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Theme.colorScheme.background.secondary
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
                modifier = Modifier.size(11.dp),
                tint = Theme.colorScheme.icon.primary
            )
        }

        Text(
            text = title,
            style = Theme.typography.body.small,
            color = Theme.colorScheme.text.body,
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
        type = null,
        subscriptionPrice = null,
        features = previewFeatures,
        isSelected = true,

    )
}


@Composable
@PreviewLightDark
private fun SubscriptionBuyCardPreview2() = SpendooTheme {
    SubscriptionBuyCard(
        icon = Res.drawable.ic_thunder,
        title = "Pro",
        description = "Advanced insights for serious budgeters.",
        type = null,
        subscriptionPrice = "7.99",
        yearlySubscriptionPrice = "95.88",
        features = previewFeatures,
        isSelected = true,

        )
}
