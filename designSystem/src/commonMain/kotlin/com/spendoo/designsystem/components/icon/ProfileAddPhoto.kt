package com.spendoo.designsystem.components.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_add_image

@Composable
fun ProfileAddPhoto(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(114.dp)
                .clip(CircleShape)
                .border(color = Theme.colorScheme.border.primary, width = 1.dp, shape = CircleShape)
        )
        Icon(
            modifier = modifier
                .size(24.dp)
                .offset((-16).dp)
                .background(Theme.colorScheme.icon.secondary, shape = CircleShape),
            painter = Res.drawable.ic_add_image.painter(),
            tint = Theme.colorScheme.brand.onPrimary,
            contentDescription = "Add photo button"
        )
    }
}

@Preview
@Composable
private fun ProfileAddPhotoPreview() {
    SpendooTheme {
        ProfileAddPhoto(
            imageUrl = "https://picsum.photos/200",
        )
    }
}