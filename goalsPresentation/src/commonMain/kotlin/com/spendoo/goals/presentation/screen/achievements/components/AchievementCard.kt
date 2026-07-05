package com.spendoo.goals.presentation.screen.achievements.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.AchievementShape
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.goals.domain.entity.Achievement
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun AchievementCard(
    achievement: Achievement,
    icon: DrawableResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (Theme.isDarkTheme) {
        Theme.colorScheme.background.tertiary
    } else {
        Theme.colorScheme.background.primary
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickableNoRipple { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AchievementShape(
                icon = icon,
                isUnlocked = achievement.isUnlocked,
                level = achievement.level
            )
            
            Text(
                text = achievement.title,
                style = Theme.typography.heading.medium,
                color = Theme.colorScheme.text.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
