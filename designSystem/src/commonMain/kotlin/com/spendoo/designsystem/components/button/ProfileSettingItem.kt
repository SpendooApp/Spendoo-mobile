package com.spendoo.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_profile_details
import spendoo.designsystem.generated.resources.ic_profile_item_arrow

@Composable
fun ProfileSettingItem(
    icon: DrawableResource,
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.background.secondary,
    shape: Shape = RoundedCornerShape(16.dp),
    onClickArrow: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickableNoRipple(onClick = onClickArrow)
            .background(backgroundColor, shape)
            .border(1.dp, Theme.colorScheme.border.primary, shape)
            .padding(8.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        CategoryIcon(icon)
        Text(
            modifier = Modifier.padding(start = 8.dp).weight(1f),
            text = title,
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.text.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Icon(
            modifier = Modifier.size(24.dp),
            painter = Res.drawable.ic_profile_item_arrow.painter(),
            tint = Theme.colorScheme.text.title,
            contentDescription = null
        )
    }
}

@PreviewLightDark
@Composable
private fun ProfileSettingItemPreview() = SpendooTheme {
    ProfileSettingItem(
        icon = Res.drawable.ic_profile_details,
        title = "Profile Details",
        onClickArrow = {}
    )
}