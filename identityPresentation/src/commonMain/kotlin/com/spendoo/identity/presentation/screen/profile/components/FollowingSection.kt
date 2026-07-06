package com.spendoo.identity.presentation.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.identity.presentation.screen.profile.ProfileInteractionListener
import com.spendoo.identity.presentation.screen.profile.ProfileUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add
import spendoo.designsystem.generated.resources.following
import spendoo.designsystem.generated.resources.ic_plus
import spendoo.designsystem.generated.resources.view_all

@Composable
fun FollowingSection(
    state: ProfileUiState,
    listener: ProfileInteractionListener
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.following),
            style = Theme.typography.title.medium,
            color = Theme.colorScheme.text.title
        )
        Text(
            modifier = Modifier.clickableNoRipple { listener.onClickViewAllFollowing() },
            text = stringResource(Res.string.view_all),
            style = Theme.typography.label.medium.medium,
            color = Theme.colorScheme.button.primary
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(70.dp)
                    .clickableNoRipple { listener.onOpenAddFollowerSheet() }
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Theme.colorScheme.button.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = Res.drawable.ic_plus.painter(),
                        tint = Theme.colorScheme.button.primary,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(Res.string.add),
                    style = Theme.typography.label.medium.medium,
                    color = Theme.colorScheme.button.primary
                )
            }
        }

        items(state.followings) { user ->
            UserAvatarChip(user = user, modifier = Modifier.clickableNoRipple { listener.onClickUser(user.userId, user.fullName, user.imageUrl) })
        }
    }
}
