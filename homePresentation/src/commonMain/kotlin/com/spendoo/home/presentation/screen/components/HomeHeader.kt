package com.spendoo.home.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.badge.Badge
import com.spendoo.designsystem.components.badge.BadgedBox
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_bell
import spendoo.designsystem.generated.resources.loading
import spendoo.designsystem.generated.resources.notifications

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    userName: String,
    userImageUrl: String?,
    notificationsCount: Long,
    isUserLoading: Boolean,
    isNotificationsLoading: Boolean,
    onNotificationClicked: () -> Unit,
    onProfileClicked: () -> Unit
) {
    TopBar(
        modifier = modifier.background(Theme.colorScheme.background.primary),
        leading = {
            AsyncImage(
                model = userImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickableNoRipple { onProfileClicked() }
            )
        },
        title = if (isUserLoading) stringResource(Res.string.loading) else userName,
        actions = listOf(
            {
                if (isNotificationsLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else if (notificationsCount > 0) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Theme.colorScheme.additional.onError,
                                contentColor = Theme.colorScheme.button.onPrimary,
                            ) {
                                Text(
                                    text = notificationsCount.toString(),
                                    style = Theme.typography.label.medium.small,
                                    color = Theme.colorScheme.button.onPrimary
                                )
                            }
                        }
                    ) {
                        SpendooIconButton(
                            iconRes = Res.drawable.ic_bell,
                            contentDescription = stringResource(Res.string.notifications),
                            onClick = onNotificationClicked,
                            tint = Theme.colorScheme.icon.primary,
                            iconSize = 24.dp
                        )
                    }
                } else {
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_bell,
                        contentDescription = stringResource(Res.string.notifications),
                        onClick = onNotificationClicked,
                        tint = Theme.colorScheme.icon.primary,
                        iconSize = 24.dp
                    )
                }
            }
        )
    )
}
