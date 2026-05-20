package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.home.presentation.screen.GoalUiState
import com.spendoo.home.presentation.screen.toDrawableResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.my_goals

@Composable
fun GoalsSection(
    goals: List<GoalUiState>,
    isLoading: Boolean,
    onViewAll: () -> Unit,
    onGoalClicked: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SectionHeader(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(Res.string.my_goals),
            onViewAll = onViewAll
        )
        if (isLoading) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(5) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .shimmerEffect()
                    )
                }
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(goals) { goalItem ->
                    GoalCard(goal = goalItem, onClick = { onGoalClicked(goalItem.id) })
                }
            }
        }
    }
}

@Composable
fun GoalCard(goal: GoalUiState, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, Theme.colorScheme.border.primary, RoundedCornerShape(20.dp))
            .background(Theme.colorScheme.background.secondary)
            .clickableNoRipple { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { goal.progress },
                modifier = Modifier.size(48.dp),
                color = Theme.colorScheme.brand.primary,
                trackColor = Theme.colorScheme.background.tertiary,
                strokeWidth = 4.dp
            )
            Icon(
                painter = goal.icon.toDrawableResource().painter(),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Theme.colorScheme.icon.primary
            )
        }
        Text(
            text = goal.name,
            style = Theme.typography.label.medium.medium,
            color = Theme.colorScheme.text.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
