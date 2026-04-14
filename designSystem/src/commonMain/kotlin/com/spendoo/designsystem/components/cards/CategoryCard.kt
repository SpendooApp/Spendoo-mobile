package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_dots
import spendoo.designsystem.generated.resources.ic_food
import spendoo.designsystem.generated.resources.ic_money
import spendoo.designsystem.generated.resources.ic_slash


@Composable
fun CategoryCard(
    icon: DrawableResource,
    title: String,
    startDate: LocalDate,
    endDate: LocalDate,
    percentage: Float,
    current: Int,
    total: Int,
    modifier : Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(24.dp),
    progressColor : Color = if (percentage < 1 ) Theme.colorScheme.icon.primary else Theme.colorScheme.additional.onError
) {
    val percentValue = (percentage * 100).roundToInt()

    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .padding(20.dp, 16.dp)

    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Box(
                modifier = Modifier.size(40.dp)
                    .background(Theme.colorScheme.button.secondary, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = icon.painter(),
                    contentDescription = null,
                    tint = Theme.colorScheme.icon.primary,
                )
            }
            Text(
                modifier = Modifier.padding(start = 8.dp).weight(1f),
                text = title,
                style = Theme.typography.title.medium,
                color = Theme.colorScheme.text.title,
                maxLines = 1,
                overflow = Ellipsis
            )
            Icon(
                modifier = Modifier.size(24.dp),
                painter = Res.drawable.ic_dots.painter(),
                contentDescription = null,
                tint = Theme.colorScheme.brand.secondaryVariant

            )
        }
        Text(
            "From ${startDate.day} ${startDate.month.name} ${startDate.year} to ${endDate.day} ${endDate.month.name} ${endDate.year}",
            modifier = Modifier.padding(vertical = 8.dp),
            style = Theme.typography.label.medium.small,
            color = Theme.colorScheme.text.titleSmall,
            maxLines = 1
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "$current",
                color = Theme.colorScheme.text.body,
                style = Theme.typography.body.medium,
            )
            Icon(
                modifier = Modifier.size(8.dp, 24.dp),
                painter = Res.drawable.ic_slash.painter(),
                contentDescription = null,
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp)
                    .weight(1f),
                text = "$total",
                color = Theme.colorScheme.text.body,
                style = Theme.typography.body.medium,
            )
            Text(
                style = Theme.typography.label.medium.medium,
                color = Theme.colorScheme.icon.primary,
                text = "$percentValue%"
            )
        }
        LinearProgressIndicator(
            progress = {
                percentage
            },
            modifier = Modifier.fillMaxWidth().height(12.dp),
            gapSize = (-10).dp,
            drawStopIndicator = {},
            color = progressColor,
            trackColor = Theme.colorScheme.button.secondary

        )
    }
}

@Preview(widthDp = 320)
@Composable
private fun CategoryCardPreview() = SpendooTheme {
    CategoryCard(
        icon = Res.drawable.ic_food,
        title = "dooooooooooooooooooooooooooooooooooooooooooooooooooo",
        startDate = LocalDate(2022, 2, 2),
        endDate = LocalDate(2022, 2, 2),
        current = 3000,
        total = 4200,
        percentage = 3000f / 4200,

    )
}