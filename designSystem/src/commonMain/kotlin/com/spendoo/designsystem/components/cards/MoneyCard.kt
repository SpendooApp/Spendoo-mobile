package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.thenIf
import com.spendoo.designsystem.modifier.thenIfNotNull
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.ui.tooling.preview.Preview
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
                modifier = Modifier.size(27.dp, 20.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
                tint = amountColor,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = if (isLoading) "Loading..." else amount,
                style = amountTextStyle,
                color = amountColor,
                maxLines = 1
            )
        }
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = if (isLoading) "Loading..." else title,
            style = titleTextStyle,
            color = titleColor,
            maxLines = 1
        )
    }
}

@Preview(widthDp = 320)
@Composable
private fun MoneyCardPreview() = SpendooTheme {
    MoneyCard(
        amount = "Total Budget",
        amountColor = Theme.colorScheme.brand.primaryVariant,
        amountTextStyle = Theme.typography.body.small,
        title = "12,000",
        titleColor = Theme.colorScheme.brand.onPrimary,
        titleTextStyle = Theme.typography.heading.large,
        backgroundColor = Theme.colorScheme.gradient.brand,
    )
}