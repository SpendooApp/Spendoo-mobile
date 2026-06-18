package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration // Added import
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_dots

@Composable
fun CardHeader(
    icon: DrawableResource,
    title: String,
    onClickMenu: () -> Unit,
    modifier: Modifier = Modifier,
    isLineThrough: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIcon(icon)
        Text(
            modifier = Modifier.padding(start = 8.dp).weight(1f),
            text = title,
            style = Theme.typography.title.medium,
            color = Theme.colorScheme.text.title,
            maxLines = 1,
            overflow = Ellipsis,
            textDecoration = if (isLineThrough) TextDecoration.LineThrough else TextDecoration.None
        )
        Icon(
            modifier = Modifier.size(24.dp).clickableNoRipple(onClick = onClickMenu),
            painter = Res.drawable.ic_dots.painter(),
            contentDescription = null,
            tint = Theme.colorScheme.brand.secondaryVariant
        )
    }
}