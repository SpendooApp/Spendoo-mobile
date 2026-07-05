package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.badge.TimeLeftBadge
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.asString
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.shared.domain.entity.CategoryIcon as DomainCategoryIcon
import com.spendoo.statistics.presentation.screen.statistics.toDrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_arrow_right
import spendoo.designsystem.generated.resources.ic_money

data class StatisticsScheduledPaymentUiState(
    val id: String,
    val name: String,
    val categoryIcon: DomainCategoryIcon,
    val amount: String,
    val date: UiText,
    val dueDateText: UiText,
    val isDueSoon: Boolean
)

@Composable
fun ScheduledPaymentRowItem(
    payment: StatisticsScheduledPaymentUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.secondary, RoundedCornerShape(cornerRadius))
            .border(1.dp, Theme.colorScheme.border.primary, RoundedCornerShape(cornerRadius))
            .clickableNoRipple(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIcon(
            icon = payment.categoryIcon.toDrawableResource(),
            size = 40.dp,
            iconTint = Theme.colorScheme.icon.primary
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = payment.name,
                style = Theme.typography.title.medium,
                color = Theme.colorScheme.text.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            TimeLeftBadge(
                timeLeft = payment.dueDateText.asString(),
                isDueSoon = payment.isDueSoon
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "-",
                    style = Theme.typography.heading.tiny,
                    color = Theme.colorScheme.text.title
                )
                Icon(
                    painter = Res.drawable.ic_money.painter(),
                    contentDescription = null,
                    tint = Theme.colorScheme.text.title,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = payment.amount,
                    style = Theme.typography.heading.tiny,
                    color = Theme.colorScheme.text.title
                )
            }
            Text(
                text = payment.date.asString(),
                style = Theme.typography.label.medium.extraSmall,
                color = Theme.colorScheme.text.titleSmall
            )
        }

        Icon(
            painter = Res.drawable.ic_arrow_right.painter(),
            contentDescription = null,
            tint = Theme.colorScheme.text.titleSmall,
            modifier = Modifier.size(18.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun ScheduledPaymentRowItemPreview() {
    SpendooTheme {
        ScheduledPaymentRowItem(
            payment = StatisticsScheduledPaymentUiState(
                id = "1",
                name = "Netflix Subscription",
                categoryIcon = DomainCategoryIcon.ENTERTAINMENT,
                amount = "15.99",
                date = UiText.DynamicString("Monthly"),
                dueDateText = UiText.DynamicString("Due in 3 days"),
                isDueSoon = false
            ),
            onClick = {}
        )
    }
}
