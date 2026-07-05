package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_cinema
import spendoo.designsystem.generated.resources.ic_food
import spendoo.designsystem.generated.resources.ic_money

@Composable
fun TopSpendingCard(
    icon: DrawableResource,
    categoryName: String,
    amount: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(16.dp),
    iconTint: Color = Theme.colorScheme.button.primary,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, shape)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CategoryIcon(icon,
                iconTint
            )

            Text(
                text = categoryName,
                style = Theme.typography.title.small,
                color = Theme.colorScheme.text.title,
            )
        }

        Row(
            modifier = Modifier
                .background(Theme.colorScheme.button.secondary, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(12.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
                tint = Theme.colorScheme.icon.primary,
            )
            Text(
                text = amount,
                style = Theme.typography.title.small,
                color = Theme.colorScheme.icon.primary,
            )
        }
    }
}

@Preview(widthDp = 380)
@Composable
private fun TopSpendingCardPreview() = SpendooTheme {
    TopSpendingCard(
        icon = Res.drawable.ic_food,
        categoryName = "Food",
        amount = "10,000",
    )
}

@Preview(widthDp = 380)
@Composable
private fun TopSpendingCardPreview2() = SpendooTheme {
    TopSpendingCard(
        icon = Res.drawable.ic_cinema,
        categoryName = "Food",
        amount = "25,500",
    )
}


