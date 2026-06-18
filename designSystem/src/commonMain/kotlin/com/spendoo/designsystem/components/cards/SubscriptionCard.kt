package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonSize
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_cinema
import spendoo.designsystem.generated.resources.ic_drink
import spendoo.designsystem.generated.resources.ic_money
import spendoo.designsystem.generated.resources.pay
import spendoo.designsystem.generated.resources.skip

@Composable
fun SubscriptionCard(
    icon: DrawableResource,
    title: String,
    amount: String,
    timeLeft: String,
    isDueSoon: Boolean,
    onSkipClick: () -> Unit,
    onPayClick: () -> Unit,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(24.dp),
    iconTint: Color = Theme.colorScheme.button.primary,
) {
    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.dp, Theme.colorScheme.border.primary, shape)
            .clickableNoRipple(onClick = onClick)
            .padding(16.dp)
    ) {

        CardDetailsHeader(
            title = title,
            icon = icon,
            timeLeft = timeLeft,
            isDueSoon = isDueSoon,
            iconTint = iconTint
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
            )

            Text(
                text = amount,
                style = Theme.typography.heading.medium,
                color = Theme.colorScheme.text.body,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            AppButton(
                modifier = Modifier
                    .width(76.dp)
                    .padding(end = 2.dp),
                type = AppButtonType.Secondary,
                onClick = onSkipClick,
                size = AppButtonSize.Small,
                text = Res.string.skip.asString()
            )

            AppButton(
                modifier = Modifier.width(76.dp),
                type = AppButtonType.Primary,
                onClick = onPayClick,
                size = AppButtonSize.Small,
                text = Res.string.pay.asString()
            )
        }
    }
}

@Preview(widthDp = 380)
@Composable
private fun SubscriptionCardPreview() = SpendooTheme {
    SubscriptionCard(
        icon = Res.drawable.ic_drink,
        title = "Daily Coffee Subscription",
        amount = "2,500",
        timeLeft = "1 hours left",
        isDueSoon = true,
        onSkipClick = { },
        onPayClick = { }
    )
}

@Preview(widthDp = 380)
@Composable
private fun SubscriptionCardPreview2() = SpendooTheme {
    SubscriptionCard(
        icon = Res.drawable.ic_cinema,
        title = "Premium Membership",
        amount = "25,500",
        timeLeft = "2 days left",
        isDueSoon = false,
        onSkipClick = { },
        onPayClick = { }
    )
}
