package com.spendoo.identity.presentation.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.identity.presentation.screen.profile.ProfileUiState
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_copy
import spendoo.designsystem.generated.resources.ic_profile_details
import spendoo.designsystem.generated.resources.ic_repeat
import spendoo.designsystem.generated.resources.id_prefix

@Composable
fun ProfileHeader(
    state: ProfileUiState,
    onCopyFollowCode: () -> Unit,
    onRegenerateCode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!state.imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = state.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(114.dp)
                    .clip(CircleShape)
                    .border(1.dp, Theme.colorScheme.border.primary, CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(114.dp)
                    .clip(CircleShape)
                    .background(Theme.colorScheme.background.secondary)
                    .border(1.dp, Theme.colorScheme.border.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(56.dp),
                    painter = Res.drawable.ic_profile_details.painter(),
                    tint = Theme.colorScheme.icon.secondary,
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = state.fullName.ifBlank { "" },
            style = Theme.typography.heading.large,
            color = Theme.colorScheme.text.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(Theme.colorScheme.background.secondary, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = stringResource(Res.string.id_prefix),
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.text.body
            )
            Text(
                text = state.followCode.ifBlank { state.userId },
                style = Theme.typography.label.medium.medium,
                color = Theme.colorScheme.text.titleSmall
            )
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .clickableNoRipple(onClick = onCopyFollowCode),
                painter = Res.drawable.ic_copy.painter(),
                tint = Theme.colorScheme.icon.secondary,
                contentDescription = null
            )
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .clickableNoRipple(onClick = onRegenerateCode),
                painter = Res.drawable.ic_repeat.painter(),
                tint = Theme.colorScheme.icon.secondary,
                contentDescription = null
            )
        }
    }
}
