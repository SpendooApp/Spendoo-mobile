package com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spendoo.categories.domain.entity.category.PriorityOption
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toStringResource
import com.spendoo.designsystem.components.surface.Surface
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString

@Composable
fun SelectedPriorityRow(
    selectedPriority: PriorityOption,
    onPrioritySelected: (PriorityOption) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Theme.colorScheme.button.secondary,
                shape = RoundedCornerShape(14.dp)
            ).padding(vertical = 4.dp),
    ) {
        PriorityOption.entries.forEach { title ->
            ToggleChip(
                text = title.toStringResource().asString(),
                selected = selectedPriority == title,
                onClick = {
                    onPrioritySelected(title)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ToggleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val animatedColor = animateColorAsState(
        targetValue = if (selected) Theme.colorScheme.button.primary else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    ).value

    val animatedTextColor = animateColorAsState(
        targetValue = if (selected) Theme.colorScheme.button.onPrimary else Theme.colorScheme.button.onSecondary,
        animationSpec = tween(durationMillis = 300)
    ).value

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = animatedColor,
    ) {
        Box(
            modifier = Modifier.padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = if (selected) Theme.typography.label.medium.medium else Theme.typography.body.small,
                color = animatedTextColor
            )
        }
    }
}