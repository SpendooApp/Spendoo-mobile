package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.spendoo.designsystem.components.general.SelectableOptionRow
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.delete
import spendoo.designsystem.generated.resources.edit
import spendoo.designsystem.generated.resources.ic_delete
import spendoo.designsystem.generated.resources.ic_edit
import spendoo.designsystem.generated.resources.select
import spendoo.designsystem.generated.resources.transaction_actions

enum class TransactionActionType(
    val icon: DrawableResource,
    val title: StringResource,
) {
    Edit(Res.drawable.ic_edit, Res.string.edit),
    Delete(Res.drawable.ic_delete, Res.string.delete)
}

@Composable
fun TransactionActionType.toTint(): Color {
    return when (this) {
        TransactionActionType.Edit -> Theme.colorScheme.text.title
        TransactionActionType.Delete -> Theme.colorScheme.additional.onError
    }
}

@Composable
fun TransactionActionsSheet(
    show: Boolean,
    isDeletable: Boolean,
    onOptionSelected: (TransactionActionType) -> Unit,
    onDismiss: () -> Unit
) {
    BottomSheet(
        isVisible = show,
        onDismiss = onDismiss
    ) {
        TransactionActionsContent(
            isDeletable = isDeletable,
            onOptionSelected = onOptionSelected,
            onDismiss = onDismiss
        )
    }
}

@Composable
fun TransactionActionsContent(
    isDeletable: Boolean,
    onOptionSelected: (TransactionActionType) -> Unit,
    onDismiss: () -> Unit
) {
    BottomSheetTemplate(
        title = stringResource(Res.string.transaction_actions),
        dismissText = stringResource(Res.string.cancel),
        showDividers = false,
        showActionButtons = false,
        onDismiss = onDismiss,
        onClickAction = { },
        actionText = stringResource(Res.string.select),
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TransactionActionType.entries.forEach { option ->
                    if (option == TransactionActionType.Delete && !isDeletable) return@forEach
                    SelectableOptionRow(
                        optionName = stringResource(option.title),
                        isSelected = false,
                        onClick = { onOptionSelected(option) },
                        customIcon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = option.icon.painter(),
                                contentDescription = null,
                                tint = option.toTint(),
                            )
                        }
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TransactionActionsContentPreview() {
    SpendooTheme {
        TransactionActionsContent(
            onOptionSelected = {},
            onDismiss = {},
            isDeletable = true
        )
    }
}
