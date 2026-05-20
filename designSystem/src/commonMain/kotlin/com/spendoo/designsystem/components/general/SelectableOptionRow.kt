package com.spendoo.designsystem.components.general

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class SelectableOption(
    val id: String,
    val text: String,
)

data class GenSelectableOption<T: Enum<T>>(
    val elem: T,
    val name: StringResource
)

@Composable
fun SelectableOptionRow(
    optionName: String,
    isSelected: Boolean,
    customIcon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colorScheme.button.primary else Theme.colorScheme.border.primary,
        label = "borderColor"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.colorScheme.background.tertiary)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .clickableNoRipple(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        customIcon?.invoke() ?: Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = borderColor, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(borderColor)
                )
            }
        }

        Text(
            modifier = Modifier.weight(1f),
            text = optionName,
            style = Theme.typography.title.medium,
            color = Theme.colorScheme.text.titleSmall,
        )
    }
}

@Composable
fun SelectableOptionRowColumn(
    options: List<SelectableOption>,
    selectedOptionId: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEach { option ->
            SelectableOptionRow(
                optionName = option.text,
                isSelected = option.id == selectedOptionId,
                onClick = { onOptionSelected(option.id) }
            )
        }
    }
}

@Composable
fun <T: Enum<T>> SelectableOptionRowColumn(
    options: List<GenSelectableOption<T>>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEach { option ->
            SelectableOptionRow(
                optionName = option.name.asString(),
                isSelected = option.elem == selectedOption,
                onClick = { onOptionSelected(option.elem) }
            )
        }
    }
}

@Composable
@Preview
fun SelectableOptionRowColumnPreview() = SpendooTheme {
    val options = listOf(
        SelectableOption("carry", "Carry over to the next period"),
        SelectableOption("move", "Move the surplus to a \"Saving\""),
        SelectableOption("reset", "Reset to original limit"),
    )
    var selectedOptionId by remember { mutableStateOf(options.first().id) }

    SelectableOptionRowColumn(
        options = options,
        selectedOptionId = selectedOptionId,
        onOptionSelected = { selectedOptionId = it },
    )
}