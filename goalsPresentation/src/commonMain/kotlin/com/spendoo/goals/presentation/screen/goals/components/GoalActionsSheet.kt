package com.spendoo.goals.presentation.screen.goals.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.general.SelectableOptionRow
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add_amount
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.goal_actions
import spendoo.designsystem.generated.resources.delete
import spendoo.designsystem.generated.resources.edit
import spendoo.designsystem.generated.resources.ic_add
import spendoo.designsystem.generated.resources.ic_delete
import spendoo.designsystem.generated.resources.ic_edit
import spendoo.designsystem.generated.resources.select

enum class GoalActionType(
    val icon: DrawableResource,
    val title: StringResource,
) {
    AddAmount(Res.drawable.ic_add, Res.string.add_amount),
    Edit(Res.drawable.ic_edit, Res.string.edit),
    Delete(Res.drawable.ic_delete, Res.string.delete)
}

@Composable
fun GoalActionType.toTint(): Color {
    return when (this) {
        GoalActionType.AddAmount -> Theme.colorScheme.text.title
        GoalActionType.Edit -> Theme.colorScheme.text.title
        GoalActionType.Delete -> Theme.colorScheme.additional.onError
    }
}

@Composable
fun GoalActionsSheet(
    show: Boolean,
    onOptionSelected: (GoalActionType?) -> Unit,
    onDismiss: () -> Unit
) {
    BottomSheet(
        isVisible = show,
        onDismiss = onDismiss
    ) {
        BottomSheetTemplate(
            title = Res.string.goal_actions.asString(),
            dismissText = Res.string.cancel.asString(),
            showDividers = false,
            showActionButtons = false,
            onDismiss = onDismiss,
            onClickAction = { },
            actionText = Res.string.select.asString(),
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GoalActionType.entries.forEach { option ->
                        SelectableOptionRow(
                            optionName = option.title.asString(),
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
}
