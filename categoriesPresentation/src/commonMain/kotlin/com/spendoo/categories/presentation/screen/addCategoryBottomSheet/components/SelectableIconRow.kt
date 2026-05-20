package com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.surface.Surface
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun SelectableIconRow(
    selectedIcon: CategoryIcon,
    onIconSelected: (CategoryIcon) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(CategoryIcon.entries){ icon ->
            IconChip(
                icon = icon.toDrawableResource(),
                selected = selectedIcon == icon,
                onClick = { onIconSelected(icon) }
            )
        }
    }
}


@Composable
private fun IconChip(
    icon: DrawableResource,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val animatedColor = animateColorAsState(
        targetValue = if (selected) Theme.colorScheme.button.primary else Theme.colorScheme.button.secondary,
    ).value

    val animatedContentColor = animateColorAsState(
        targetValue = if (selected) Theme.colorScheme.button.onPrimary else Theme.colorScheme.button.primary,
    ).value

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = animatedColor,
        modifier = Modifier.size(56.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = icon.painter(),
                contentDescription = null,
                tint = animatedContentColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}