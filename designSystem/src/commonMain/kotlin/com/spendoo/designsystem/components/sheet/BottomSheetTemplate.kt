package com.spendoo.designsystem.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.general.SelectableOption
import com.spendoo.designsystem.components.general.SelectableOptionRowColumn
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BottomSheetTemplate(
    title: String,
    dismissText: String,
    onDismiss: () -> Unit,
    onClickAction: () -> Unit,
    actionText: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
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

        content()
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
                text = dismissText,
            )
            AppButton(
                modifier = Modifier.weight(1f),
                type = AppButtonType.Primary,
                onClick = onClickAction,
                text = actionText,
            )
        }
    }
}

@Preview
@Composable
fun BottomSheetTemplatePreview() = SpendooTheme {
    val options = listOf(
        SelectableOption("carry", "Carry over to the next period"),
        SelectableOption("move", "Move the surplus to a \"Saving\""),
        SelectableOption("reset", "Reset to original limit"),
    )
    var selectedOptionId by remember { mutableStateOf(options.first().id) }

    BottomSheetTemplate(
        title = "Leftover Funds Action",
        dismissText = "Cancel",
        actionText = "Select",
        onDismiss = {},
        onClickAction = {},
    ) {
        SelectableOptionRowColumn(
            modifier = Modifier.background(Color.Red),
            options = options,
            selectedOptionId = selectedOptionId,
            onOptionSelected = { selectedOptionId = it },
        )
    }
}
