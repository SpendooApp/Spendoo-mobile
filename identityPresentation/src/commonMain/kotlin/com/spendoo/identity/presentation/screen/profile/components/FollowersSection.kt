package com.spendoo.identity.presentation.screen.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.presentation.screen.profile.ProfileInteractionListener
import com.spendoo.identity.presentation.screen.profile.ProfileUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.followers
import spendoo.designsystem.generated.resources.no_followers
import spendoo.designsystem.generated.resources.view_all

@Composable
fun FollowersSection(
    state: ProfileUiState,
    listener: ProfileInteractionListener
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.followers),
            style = Theme.typography.title.medium,
            color = Theme.colorScheme.text.title
        )
        Text(
            modifier = Modifier.clickableNoRipple { listener.onClickViewAllFollowers() },
            text = stringResource(Res.string.view_all),
            style = Theme.typography.label.medium.medium,
            color = Theme.colorScheme.button.primary
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (state.followers.isEmpty() && !state.isFollowersLoading) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(Res.string.no_followers),
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.text.title
        )
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
    ) {
        if (state.isFollowersLoading) {
            items(5) {
                UserAvatarShimmer()
            }
        } else {
            items(state.followers) { user ->
                UserAvatarChip(user = user)
            }
        }
    }
}
