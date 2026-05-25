package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.modifier.thenIfNotNull
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import androidx.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_money

@Composable
fun MoneyCard(
    isLoading: Boolean = false,
    amount: String,
    amountColor: Color,
    amountTextStyle: TextStyle,
    title: String,
    titleColor: Color,
    titleTextStyle: TextStyle,
    titleIcon: @Composable (() -> Unit)? = null,
    backgroundColor: Brush,
    modifier: Modifier = Modifier,
    borderColor: Color? = null,
    shape: Shape = RoundedCornerShape(24.dp),
) {
    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .thenIfNotNull(borderColor) { color ->
                this.border(1.dp, color, shape)
            }
            .padding(20.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(27.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
                tint = amountColor,
            )
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(width = 80.dp, height = 24.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
            } else {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = amount,
                    style = amountTextStyle,
                    color = amountColor,
                    maxLines = 1
                )
            }
        }
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )
            } else {
                titleIcon?.invoke()
                Text(
                    text = title,
                    style = titleTextStyle,
                    color = titleColor,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(widthDp = 320)
@Composable
private fun MoneyCardPreview() = SpendooTheme {
    MoneyCard(
        amount = "12,000",
        amountColor = Theme.colorScheme.brand.primaryVariant,
        amountTextStyle = Theme.typography.heading.large,
        title = "Total Budget",
        titleColor = Theme.colorScheme.brand.onPrimary,
        titleTextStyle = Theme.typography.body.small,
        backgroundColor = Theme.colorScheme.gradient.brand,
    )
}

