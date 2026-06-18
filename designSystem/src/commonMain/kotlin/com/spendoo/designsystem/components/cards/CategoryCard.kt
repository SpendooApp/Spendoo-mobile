package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_food
import spendoo.designsystem.generated.resources.ic_money

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
    onClickMenu: () -> Unit
) {
    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.dp, Theme.colorScheme.border.primary, shape)
            .padding(20.dp, 16.dp)
    ) {
        CardHeader(icon, title, onClickMenu)
        when (budgetData) {
            null -> NoBudgetContent(current)
            else -> {
                Text(
                    "From ${budgetData.startDate.day} ${budgetData.startDate.month.name} ${budgetData.startDate.year} to ${budgetData.endDate.day} ${budgetData.endDate.month.name} ${budgetData.endDate.year}",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.text.titleSmall,
                    maxLines = 1
                )
                ProgressSection(budgetData.percentage, budgetData.total, current)
            }
        }
    }
}


@Composable
private fun NoBudgetContent(current: Int) {
    Row(
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

@Preview(widthDp = 320)
@Composable
private fun CategoryCardPreview() = SpendooTheme {
    CategoryCard(
        icon = Res.drawable.ic_food,
        title = "dooooooooooooooooooooooooooooooooooooooooooooooooooo",
        current = 3000,
        onClickMenu = {},
        budgetData = null
    )
}

@Preview(widthDp = 320)
@Composable
private fun CategoryCardPreview2() = SpendooTheme {
    CategoryCard(
        icon = Res.drawable.ic_food,
        title = "dooooooooooooooooooooooooooooooooooooooooooooooooooo",
        current = 3000,
        budgetData = BudgetDataUiState(
            startDate = LocalDate(2022, 2, 2),
            endDate = LocalDate(2022, 2, 2),
            total = 4200,
            percentage = 50,
        ),
        onClickMenu = {},
    )
}

