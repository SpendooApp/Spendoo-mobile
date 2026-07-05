package com.spendoo.identity.presentation.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.ProfileSettingItem
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.switch.Switch
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.identity.presentation.screen.profile.ProfileInteractionListener
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.achievements
import spendoo.designsystem.generated.resources.categories
import spendoo.designsystem.generated.resources.change_language
import spendoo.designsystem.generated.resources.change_theme
import spendoo.designsystem.generated.resources.ic_bell
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_mobile
import spendoo.designsystem.generated.resources.ic_profile_details
import spendoo.designsystem.generated.resources.ic_repeat
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.img_coin
import spendoo.designsystem.generated.resources.notification_setting
import spendoo.designsystem.generated.resources.profile_details
import spendoo.designsystem.generated.resources.scheduled_payments

@Composable
fun ProfileSettingsList(
    listener: ProfileInteractionListener
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ProfileSettingItem(
            icon = Res.drawable.ic_profile_details,
            title = stringResource(Res.string.profile_details),
            onClickArrow = { listener.onClickProfileDetails() }
        )
        ProfileSettingItem(
            icon = Res.drawable.ic_categories,
            title = stringResource(Res.string.categories),
            onClickArrow = { listener.onClickCategories() }
        )
        ProfileSettingItem(
            icon = Res.drawable.ic_repeat,
            title = stringResource(Res.string.scheduled_payments),
            onClickArrow = { listener.onClickScheduledPayments() }
        )
        ProfileSettingItem(
            icon = Res.drawable.img_coin,
            title = stringResource(Res.string.achievements),
            onClickArrow = { listener.onClickAchievements() }
        )
        ProfileSettingItem(
            icon = Res.drawable.ic_bell,
            title = stringResource(Res.string.notification_setting),
            onClickArrow = { listener.onClickNotificationSetting() }
        )
        ProfileSettingItem(
            icon = Res.drawable.ic_mobile,
            title = stringResource(Res.string.change_language),
            onClickArrow = { listener.onClickChangeLanguage() }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colorScheme.background.secondary, RoundedCornerShape(16.dp))
                .border(1.dp, Theme.colorScheme.border.primary, RoundedCornerShape(16.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Theme.colorScheme.button.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = Res.drawable.ic_stats.painter(),
                    tint = Theme.colorScheme.button.primary,
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                text = stringResource(Res.string.change_theme),
                style = Theme.typography.body.medium,
                color = Theme.colorScheme.text.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            val isDark = Theme.isDarkTheme
            Switch(
                checked = isDark,
                onCheckedChange = { listener.onToggleTheme(!isDark) }
            )
        }
    }
}
