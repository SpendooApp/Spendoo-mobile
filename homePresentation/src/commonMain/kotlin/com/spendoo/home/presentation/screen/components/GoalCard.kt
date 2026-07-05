package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.home.presentation.screen.GoalUiState
import com.spendoo.home.presentation.screen.toDrawableResource
import com.spendoo.shared.domain.entity.CategoryIcon

@Composable
fun GoalCard(goal: GoalUiState, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .widthIn(min = 80.dp)
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
                progress = { 1f },
                modifier = Modifier.size(48.dp),
                color = Theme.colorScheme.button.secondary,
                trackColor = Theme.colorScheme.background.tertiary,
                strokeWidth = 4.dp
            )
            CircularProgressIndicator(
                progress = { goal.progress },
                modifier = Modifier.size(48.dp),
                color = Theme.colorScheme.icon.primary,
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

@Composable
@PreviewLightDark
fun GoalCardPreview() {
    SpendooTheme {
        GoalCard(
            goal = GoalUiState(
                id = "1",
                name = "Vacation",
                icon = CategoryIcon.ENTERTAINMENT,
                progress = 0.75f
            ),
            onClick = {}
        )
    }
}