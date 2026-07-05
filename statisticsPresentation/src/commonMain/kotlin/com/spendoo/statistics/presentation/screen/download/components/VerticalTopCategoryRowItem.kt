package com.spendoo.statistics.presentation.screen.download.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun VerticalTopCategoryRowItem(
    category: CategorySpending,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.secondary, RoundedCornerShape(24.dp))
            .border(1.dp, Theme.colorScheme.button.secondary, RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            CategoryIcon(
                icon = category.categoryIcon.toDrawableResource(),
                size = 48.dp,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = category.categoryName,
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.brand.onSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.padding(end = 2.dp).size(12.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
                tint = Theme.colorScheme.text.titleSmall
            )
            Text(
                text = category.spending.toInt().toString(),
                style = Theme.typography.heading.tiny,
                color = Theme.colorScheme.text.titleSmall,
                modifier = Modifier.padding(end = 16.dp)
            )
            val isIncrease = category.percentageChange > 0
            val percentColor =
                if (isIncrease) Theme.colorScheme.additional.onError else Theme.colorScheme.additional.onSuccess
            val percentText =
                "${if (isIncrease) "+" else ""}${category.percentageChange.toInt()}%"
            Text(
                text = percentText,
                style = Theme.typography.label.medium.small,
                color = percentColor
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun VerticalTopCategoryRowItemPreview() = SpendooTheme {
    VerticalTopCategoryRowItem(
        category = CategorySpending(
            categoryName = "Food & Drinks",
            categoryIcon = CategoryIcon.FOOD,
            spending = 150.0,
            contributionPercentage = 25.0,
            categoryId = "1",
            percentageChange = 5.0
        ),
    )
}