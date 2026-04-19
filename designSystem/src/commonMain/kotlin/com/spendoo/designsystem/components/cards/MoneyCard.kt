package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_money

@Composable
fun MoneyCard(
    title: String,
    amount: String,
    backgroundColor: Brush,
    iconTint: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),

    ) {
    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .padding(20.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Row(

        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
                tint = iconTint,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = title,
                style = Theme.typography.heading.large,
                color = Theme.colorScheme.brand.onPrimary,
                maxLines = 1
            )
        }
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = amount,
            style = Theme.typography.body.small,
            color = Theme.colorScheme.brand.primaryVariant,
            maxLines = 1
        )

    }
}

@Preview(widthDp = 320)
@Composable
private fun MoneyCardPreview() = SpendooTheme {
    MoneyCard(
        title = "12,000",
        amount = "Total Budget",
        backgroundColor = Theme.colorScheme.gradient.brand,
        iconTint = Theme.colorScheme.additional.onSuccess,
    )
}