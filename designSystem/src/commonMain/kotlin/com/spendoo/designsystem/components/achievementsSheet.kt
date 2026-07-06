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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.color.scheme.toBrush
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.claim_badge
import spendoo.designsystem.generated.resources.congratulations
import spendoo.designsystem.generated.resources.ic_stars
import spendoo.designsystem.generated.resources.img_coin
import spendoo.designsystem.generated.resources.you_have_unlocked_a_new_badge

@Composable
fun AchievementBottomSheet(
    isVisible: Boolean = true,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    level: Long,
    isUnlocked: Boolean,
    icon: DrawableResource,
    type: String,
    name: String,
    description: String
) {
    val backgroundColor = if(isUnlocked) {
        if (Theme.isDarkTheme)
            Theme.colorScheme.background.tertiary
        else
            Theme.colorScheme.primary.variant200
    } else {
        if (Theme.isDarkTheme)
            Theme.colorScheme.background.quinary
        else
            Theme.colorScheme.text.title
    }

    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        skipPartiallyExpanded = true,
        horizontalPadding = 0.dp,
        containerColor = backgroundColor,
        content = {
            AchievementsBottomSheetContent(
                modifier = modifier,
                level = level,
                icon = icon,
                type = type,
                name = name,
                backgroundColor = backgroundColor,
                isUnlocked = isUnlocked,
                description = description,
                onDismiss = onDismiss
            )
        }
    )
}

@Composable
private fun AchievementsBottomSheetContent(
    level: Long,
    icon: DrawableResource,
    type: String,
    name: String,
    backgroundColor: Color,
    description: String,
    isUnlocked: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val typeBackgroundColor = if (isUnlocked) {
        Theme.colorScheme.gradient.brandVertical
    } else {
        Theme.colorScheme.text.label.toBrush()
    }

    val titleColor = if (isUnlocked) {
        Theme.colorScheme.text.headingBlue
    } else {
        Theme.colorScheme.button.onPrimary
    }

    val descriptionColor = if (isUnlocked) {
        Theme.colorScheme.text.headingBlue
    } else {
        Theme.colorScheme.text.body
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(start = 45.dp, end = 45.dp, top = 65.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (isUnlocked) {
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
            }
            AchievementShape(
                level = level,
                icon = icon,
                isUnlocked = isUnlocked
            )
            Box(
                modifier = Modifier
                    .size(80.dp, 25.dp)
                    .background(
                        brush = typeBackgroundColor,
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
                    color = titleColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = description,
                    style = Theme.typography.body.extraSmall,
                    color = descriptionColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }

            if (isUnlocked) {
                AppButton(
                    type = AppButtonType.Primary,
                    onClick = onDismiss,
                    text = Res.string.claim_badge.asString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
            }
        }
        if (isUnlocked) {
            Icon(
                painter = Res.drawable.ic_stars.painter(),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 12.dp),
                tint = Theme.colorScheme.additional.golden
            )
        }
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
        isUnlocked = true,
        description = "Make your very first savings deposit",
        onDismiss = {},
        backgroundColor = Theme.colorScheme.background.tertiary
    )
}

@PreviewLightDark
@Composable
private fun AchievementsPreview2() = SpendooTheme {
    AchievementsBottomSheetContent(
        level = 1,
        icon = Res.drawable.img_coin,
        type = "Savings",
        name = "First Saver",
        isUnlocked = false,
        description = "Make your very first savings deposit",
        onDismiss = {},
        backgroundColor = Theme.colorScheme.background.quinary
    )
}
