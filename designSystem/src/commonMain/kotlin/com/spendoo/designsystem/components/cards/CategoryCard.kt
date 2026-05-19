package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
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
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_dots
import spendoo.designsystem.generated.resources.ic_food
import spendoo.designsystem.generated.resources.ic_money
import spendoo.designsystem.generated.resources.ic_slash

data class BudgetDataUiState(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val percentage: Int,
    val total: Int,
)

@Composable
fun CategoryCard(
    icon: DrawableResource,
    title: String,
    current: Int,
    budgetData: BudgetDataUiState?,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(24.dp),
    progressColor: Color = if (budgetData == null || budgetData.percentage < 100) Theme.colorScheme.icon.primary else Theme.colorScheme.additional.onError,
    onClickMenu: () -> Unit
) {
    val percentValue = budgetData?.percentage

    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .padding(20.dp, 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryIcon(icon)
            Text(
                modifier = Modifier.padding(start = 8.dp).weight(1f),
                text = title,
                style = Theme.typography.title.medium,
                color = Theme.colorScheme.text.title,
                maxLines = 1,
                overflow = Ellipsis
            )
            Icon(
                modifier = Modifier.size(24.dp).clickableNoRipple(onClick = onClickMenu),
                painter = Res.drawable.ic_dots.painter(),
                contentDescription = null,
                tint = Theme.colorScheme.brand.secondaryVariant
            )
        }
        budgetData?.let { budgetData ->
            Text(
                "From ${budgetData.startDate.day} ${budgetData.startDate.month.name} ${budgetData.startDate.year} to ${budgetData.endDate.day} ${budgetData.endDate.month.name} ${budgetData.endDate.year}",
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
                    text = "${budgetData.total}",
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
                    budgetData.percentage / 100f
                },
                modifier = Modifier.fillMaxWidth().height(12.dp),
                gapSize = (-10).dp,
                drawStopIndicator = {},
                color = progressColor,
                trackColor = Theme.colorScheme.button.secondary

            )
        } ?: Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = Res.drawable.ic_money.painter(),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "${if (current > 0) "-" else ""}$current",
                color = Theme.colorScheme.text.body,
                style = Theme.typography.body.medium,
            )
        }
    }
}


@Preview(widthDp = 320)
@Composable
private fun CategoryCardPreview() = SpendooTheme {
    CategoryCard(
        icon = Res.drawable.ic_food,
        title = "dooooooooooooooooooooooooooooooooooooooooooooooooooo",
        current = 3000,
//        budgetData = BudgetDataUiState(
//            startDate = LocalDate(2022, 2, 2),
//            endDate = LocalDate(2022, 2, 2),
//            total = 4200,
//            percentage = 3000f / 4200,
//        )
        onClickMenu = {},
        budgetData = null
    )
}