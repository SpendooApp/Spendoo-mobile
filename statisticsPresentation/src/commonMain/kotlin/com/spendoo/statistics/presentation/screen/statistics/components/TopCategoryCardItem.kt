package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.statistics.domain.entity.CategorySpending
import com.spendoo.statistics.presentation.screen.statistics.toDrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_money

@Composable
fun TopCategoryCardItem(
    category: CategorySpending,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .widthIn(min = 96.dp)
            .background(Theme.colorScheme.background.secondary, RoundedCornerShape(24.dp))
            .border(1.dp, Theme.colorScheme.button.secondary, RoundedCornerShape(24.dp))
            .padding(vertical = 8.dp)
    ) {
        CategoryIcon(
            icon = category.categoryIcon.toDrawableResource(),
            size = 48.dp,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = category.categoryName,
            style = Theme.typography.label.medium.small,
            color = Theme.colorScheme.brand.onSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    modifier = Modifier.size(8.dp),
                    painter = Res.drawable.ic_money.painter(),
                    contentDescription = null,
                    tint = Theme.colorScheme.brand.secondaryVariant
                )
                Text(
                    text = category.spending.toInt().toString(),
                    style = Theme.typography.label.medium.extraSmall,
                    color = Theme.colorScheme.brand.secondaryVariant
                )
            }
            val isIncrease = category.percentageChange > 0
            val percentColor =
                if (isIncrease) Theme.colorScheme.additional.onError else Theme.colorScheme.additional.onSuccess
            val percentText =
                "${if (isIncrease) "+" else ""}${category.percentageChange.toInt()}%"
            Text(
                text = percentText,
                style = Theme.typography.label.medium.extraSmall,
                color = percentColor
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TopCategoryCardItemPreview() {
    SpendooTheme {
        TopCategoryCardItem(
            category = CategorySpending(
                categoryId = "1",
                categoryName = "Food",
                categoryIcon = CategoryIcon.FOOD,
                spending = 120.0,
                percentageChange = 12.5,
                contributionPercentage = 30.0
            ),
        )
    }
}

@PreviewLightDark
@Composable
private fun TopCategoryCardItemPreview2() {
    SpendooTheme {
        TopCategoryCardItem(
            category = CategorySpending(
                categoryId = "2",
                categoryName = "Food",
                categoryIcon = CategoryIcon.FOOD,
                spending = 120.0,
                percentageChange = -12.5,
                contributionPercentage = 30.0
            ),
        )
    }
}
