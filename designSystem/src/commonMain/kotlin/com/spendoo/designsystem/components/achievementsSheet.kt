package com.spendoo.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.congratulations
import spendoo.designsystem.generated.resources.ic_stars
import spendoo.designsystem.generated.resources.img_coin
import spendoo.designsystem.generated.resources.you_have_unlocked_a_new_badge

@Composable
fun AchievementsBottomSheet(
    isVisible: Boolean = true,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    level: Int,
    icon: DrawableResource,
    type: String,
    name: String,
    description: String
) {
    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        horizontalPadding = 0.dp,
        content = {
            AchievementsBottomSheetContent(
                modifier = modifier,
                level = level,
                icon = icon,
                type = type,
                name = name,
                description = description
            )
        }
    )
}

@Composable
private fun AchievementsBottomSheetContent(
    modifier: Modifier = Modifier,
    level: Int,
    icon: DrawableResource,
    type: String,
    name: String,
    description: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.padding(45.dp, 65.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = Res.string.congratulations.asString(),
                    style = Theme.typography.heading.medium,
                    color = Theme.colorScheme.text.headingBlue,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = Res.string.you_have_unlocked_a_new_badge.asString(),
                    style = Theme.typography.body.extraSmall,
                    color = Theme.colorScheme.text.headingBlue,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            AchievementShape(
                level = level,
                icon = icon,
                isUnlocked = true
            )
            Box(
                modifier = Modifier
                    .size(80.dp, 25.dp)
                    .background(
                        brush = Theme.colorScheme.gradient.brandVertical,
                        shape = RoundedCornerShape(360.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = type,
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.button.onPrimary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = name,
                    style = Theme.typography.heading.medium,
                    color = Theme.colorScheme.text.headingBlue,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = description,
                    style = Theme.typography.body.extraSmall,
                    color = Theme.colorScheme.text.headingBlue,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }
            AppButton(
                type = AppButtonType.Primary,
                onClick = {},
                text = "Claim Badge",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )

        }
        Icon(
            painter = Res.drawable.ic_stars.painter(),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 12.dp),
            tint = Theme.colorScheme.additional.golden
        )

    }

}

@PreviewLightDark
@Composable
private fun AchievementsPreview() = SpendooTheme {
    AchievementsBottomSheetContent(
        level = 1,
        icon = Res.drawable.img_coin,
        type = "Savings",
        name = "First Saver",
        description = "Make your very first savings deposit"
    )
}
