package com.spendoo.designsystem.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

data class BottomSheetOption(
    val id: String,
    val text: String,
)

@Composable
fun BottomSheetTemplate(
    title: String,
    onSelect: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    cancelText: String = "Cancel",
    selectText: String = "Select",
    isSelectEnabled: Boolean = true,
    body: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .background(color = Theme.colorScheme.background.tertiary)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            textAlign = TextAlign.Start,
            text = title,
            style = Theme.typography.title.large,
            color = Theme.colorScheme.text.title,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = body,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppButton(
                modifier = Modifier.weight(1f),
                type = AppButtonType.Secondary,
                onClick = onDismiss,
                text = cancelText,
            )
            AppButton(
                modifier = Modifier.weight(1f),
                type = AppButtonType.Primary,
                state = if (isSelectEnabled) AppButtonState.Enabled else AppButtonState.Disabled,
                onClick = onSelect,
                text = selectText,
            )
        }
    }
}

@Composable
fun BottomSheetOptionsBody(
    options: List<BottomSheetOption>,
    selectedOptionId: String?,
    onOptionSelected: (BottomSheetOption) -> Unit,
) {
    options.forEach { option ->
        SelectableOptionRow(
            option = option,
            isSelected = option.id == selectedOptionId,
            onClick = { onOptionSelected(option) },
        )
    }
}

@Composable
private fun SelectableOptionRow(
    option: BottomSheetOption,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val borderColor =
        if (isSelected) Theme.colorScheme.button.primary else Theme.colorScheme.border.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.colorScheme.background.tertiary)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
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
                        .background(Theme.colorScheme.button.primary)
                )
            }
        }

        Text(
            modifier = Modifier.weight(1f),
            text = option.text,
            style = Theme.typography.title.medium,
            color = Theme.colorScheme.text.titleSmall,
        )
    }
}

@Preview(widthDp = 380)
@Composable
fun BottomSheetTemplatePreview() = SpendooTheme {
    val options = listOf(
        BottomSheetOption("carry", "Carry over to the next period"),
        BottomSheetOption("move", "Move the surplus to a \"Saving\""),
        BottomSheetOption("reset", "Reset to original limit"),
    )
    var selectedOptionId by remember { mutableStateOf(options.first().id) }

    BottomSheetTemplate(
        title = "Leftover Funds Action",
        onSelect = {},
        onDismiss = {},
        isSelectEnabled = true,
    ) {
        BottomSheetOptionsBody(
            options = options,
            selectedOptionId = selectedOptionId,
            onOptionSelected = { selectedOptionId = it.id },
        )
    }
}
