package com.spendoo.identity.presentation.screen.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.ProfileSettingItem
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.presentation.screen.profile.ProfileInteractionListener
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.achievements
import spendoo.designsystem.generated.resources.categories
import spendoo.designsystem.generated.resources.change_language
import spendoo.designsystem.generated.resources.change_theme
import spendoo.designsystem.generated.resources.home_offers
import spendoo.designsystem.generated.resources.ic_bell
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_logout
import spendoo.designsystem.generated.resources.ic_mobile
import spendoo.designsystem.generated.resources.ic_money_in_offer
import spendoo.designsystem.generated.resources.ic_profile_details
import spendoo.designsystem.generated.resources.ic_repeat
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.ic_win_cup
import spendoo.designsystem.generated.resources.logout
import spendoo.designsystem.generated.resources.notifications
import spendoo.designsystem.generated.resources.periodic_reminder
import spendoo.designsystem.generated.resources.profile_details
import spendoo.designsystem.generated.resources.scheduled_payments

@Composable
fun ProfileSettingsList(
    isReminderEnabled: Boolean,
    isHomeOffersEnabled: Boolean,
    listener: ProfileInteractionListener,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
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
            icon = Res.drawable.ic_win_cup,
            title = stringResource(Res.string.achievements),
            onClickArrow = { listener.onClickAchievements() }
        )
        ProfileSettingItem(
            icon = Res.drawable.ic_bell,
            title = stringResource(Res.string.notifications),
            onClickArrow = { listener.onClickNotificationSetting() }
        )
        ProfileSettingItem(
            icon = Res.drawable.ic_mobile,
            title = stringResource(Res.string.change_language),
            onClickArrow = { listener.onClickChangeLanguage() }
        )

        val isDark = Theme.isDarkTheme
        ProfileSettingItem(
            icon = Res.drawable.ic_stats,
            title = stringResource(Res.string.change_theme),
            checked = isDark,
            onCheckedChange = { listener.onToggleTheme(!isDark) }
        )

        ProfileSettingItem(
            icon = Res.drawable.ic_bell,
            title = stringResource(Res.string.periodic_reminder),
            checked = isReminderEnabled,
            onCheckedChange = { listener.onToggleReminder(it) }
        )

        ProfileSettingItem(
            icon = Res.drawable.ic_money_in_offer,
            title = stringResource(Res.string.home_offers),
            checked = isHomeOffersEnabled,
            onCheckedChange = { listener.onToggleHomeOffers(it) }
        )

        ProfileSettingItem(
            icon = Res.drawable.ic_logout,
            title = stringResource(Res.string.logout),
            onClickArrow = { listener.onClickLogout() }
        )
    }
}
