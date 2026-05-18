package com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.categories.domain.entity.category.ResetCycleOption
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toStringResource
import com.spendoo.designsystem.components.surface.Surface
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString

@Composable
fun SelectableResetCycleRow(
    selectedCycle: ResetCycleOption,
    onCycleSelected: (ResetCycleOption) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(ResetCycleOption.entries) { title ->
            CycleChip(
                text = title.toStringResource().asString(),
                selected = selectedCycle == title,
                onClick = { onCycleSelected(title) }
            )
        }
    }
}


@Composable
private fun CycleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val animatedColor = animateColorAsState(
        targetValue = if (selected) Theme.colorScheme.border.active else Theme.colorScheme.button.onSecondary,
    ).value

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = Theme.colorScheme.button.secondary,
        border = BorderStroke(
            width = 1.dp,
            color = Theme.colorScheme.button.primary
        ).takeIf { selected }
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = Theme.typography.label.medium.medium,
                color = animatedColor
            )
        }
    }
}