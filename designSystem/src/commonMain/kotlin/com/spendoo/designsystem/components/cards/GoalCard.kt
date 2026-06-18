package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_food

data class GoalDataUiState(
    val targetDate: LocalDate,
    val priority: Priority,
    val percentage: Int,
    val total: Int,
)

@Composable
fun GoalCard(
    icon: DrawableResource,
    title: String,
    current: Int,
    budgetData: GoalDataUiState,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(24.dp),
    onClickMenu: () -> Unit
) {
    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.dp, Theme.colorScheme.border.primary, shape)
            .padding(20.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        CardHeader(icon, title, onClickMenu, isLineThrough = budgetData.priority == Priority.Done)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PriorityCard(budgetData.priority)
            Text(
                "Target: ${budgetData.targetDate.day} ${budgetData.targetDate.month.name} ${budgetData.targetDate.year}", //TODO: translate
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.brand.secondaryVariant,
            )
        }
        ProgressSection(budgetData.percentage, budgetData.total, current, progressColor = if (budgetData.percentage < 100) Theme.colorScheme.icon.primary else Theme.colorScheme.additional.onSuccess)
    }
}

@Preview(widthDp = 320)
@Composable
private fun GoalCardPreview() = SpendooTheme {
    GoalCard(
        icon = Res.drawable.ic_food,
        title = "dooooooooooooooooooooooooooooooooooooooooooooooooooo",
        current = 3000,
        onClickMenu = {},
        budgetData = GoalDataUiState(
            targetDate = LocalDate(2022, 2, 2),
            priority = Priority.MEDIUM,
            percentage = 50,
            total = 4200,
        )
    )
}

@Preview(widthDp = 320)
@Composable
private fun GoalCardPreview2() = SpendooTheme {
    GoalCard(
        icon = Res.drawable.ic_food,
        title = "dooooooooooooooooooooooooooooooooooooooooooooooooooo",
        current = 3000,
        onClickMenu = {},
        budgetData = GoalDataUiState(
            targetDate = LocalDate(2022, 2, 2),
            priority = Priority.Done,
            percentage = 100,
            total = 4200,
        )
    )
}
