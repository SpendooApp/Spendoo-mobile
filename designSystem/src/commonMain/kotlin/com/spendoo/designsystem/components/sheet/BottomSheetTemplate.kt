package com.spendoo.designsystem.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.general.SelectableOption
import com.spendoo.designsystem.components.general.SelectableOptionRowColumn
import com.spendoo.designsystem.components.indicator.DotsProgressIndicator
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomSheetTemplate(
    title: String,
    dismissText: String,
    onDismiss: () -> Unit,
    onClickAction: () -> Unit,
    actionText: String,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null,
    showDividers: Boolean = true,
    showActionButtons: Boolean = true,
    actionButtonState: AppButtonState = AppButtonState.Enabled,
    backgroundColor: Color = Theme.colorScheme.background.tertiary,
    content: LazyListScope.(listState: LazyListState) -> Unit
) {
    val listState = rememberLazyListState()

    Box {
        LazyColumn(
            modifier = modifier
                .background(color = backgroundColor)
                .fillMaxWidth(),
            state = listState,
        ) {
            stickyHeader {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = backgroundColor)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            textAlign = TextAlign.Start,
                            text = title,
                            style = Theme.typography.title.large,
                            color = Theme.colorScheme.text.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        trailingContent?.invoke()
                    }
                    if (showDividers) { HorizontalDivider(Modifier.padding(horizontal = 16.dp)) }
                }
            }

            content(listState)

            if (showActionButtons) {
                item {
                    Spacer(modifier = Modifier.height(64.dp + 8.dp))
                }
            }
        }
        if (showActionButtons) {
            Column(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
                if (showDividers) {
                    HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                }
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val halfWidth = maxWidth / 2

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = backgroundColor)
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AppButton(
                            modifier = Modifier.weight(1f),
                            type = AppButtonType.Secondary,
                            onClick = onDismiss,
                            text = dismissText,
                        )
                        AppButton(
                            modifier = Modifier
                                // 1. If text is larger, widthIn lets it expand naturally up to full width
                                // 2. Forces the button to be AT LEAST half the screen width (minus spacing)
                                .wrapContentWidth()
                                .widthIn(min = halfWidth - 4.dp),
                            state = actionButtonState,
                            type = AppButtonType.Primary,
                            onClick = onClickAction,
                            text = actionText,
                            loadingIcon = {
                                DotsProgressIndicator()
                            }
                        )
                    }
                }
            }
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
        actionText = "Select anything",
        onDismiss = {},
        onClickAction = {},
    ) {
        item {
            SelectableOptionRowColumn(
                modifier = Modifier.background(Color.Red),
                options = options,
                selectedOptionId = selectedOptionId,
                onOptionSelected = { selectedOptionId = it },
            )
        }
    }
}


