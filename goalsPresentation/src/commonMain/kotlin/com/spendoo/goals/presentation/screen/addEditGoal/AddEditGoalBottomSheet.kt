package com.spendoo.goals.presentation.screen.addEditGoal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.goals.domain.entity.PriorityOption
import com.spendoo.shared.domain.utils.toCleanDoubleOrNull
import com.spendoo.shared.domain.utils.toCleanString
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.components.general.AppSegmentedControl
import com.spendoo.designsystem.components.general.GenSelectableOption
import com.spendoo.designsystem.components.row.SelectableIconRow
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.format
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.goals.presentation.screen.goals.components.toDrawableResource
import com.spendoo.shared.domain.entity.CategoryIcon
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.priority
import spendoo.designsystem.generated.resources.icon
import spendoo.designsystem.generated.resources.add_new_goal
import spendoo.designsystem.generated.resources.edit_goal
import spendoo.designsystem.generated.resources.enter_goal_name
import spendoo.designsystem.generated.resources.enter_your_target_amount
import spendoo.designsystem.generated.resources.enter_your_target_date
import spendoo.designsystem.generated.resources.save_goal
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.low
import spendoo.designsystem.generated.resources.medium
import spendoo.designsystem.generated.resources.high

@Composable
fun AddEditGoalBottomSheet(
    isVisible: Boolean,
    initialAddEditGoalUiState: AddEditGoalUiState?,
    onDismiss: () -> Unit,
    onAddGoal: (AddEditGoalUiState) -> Unit,
    viewModel: AddGoalViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(isVisible, initialAddEditGoalUiState) {
        if (isVisible) {
            viewModel.init(initialAddEditGoalUiState)
        }
    }

    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        horizontalPadding = 0.dp
    ) {
        AddEditGoalContent(
            isEditing = state.goalId != null,
            isLoading = state.isLoading,
            addEditGoalUiState = state,
            interactionListener = viewModel,
            onDismiss = onDismiss,
            onSubmit = {
                viewModel.submit(onSuccess = onAddGoal)
            }
        )
    }
}

@Composable
private fun AddEditGoalContent(
    isEditing: Boolean,
    addEditGoalUiState: AddEditGoalUiState,
    interactionListener: AddGoalInteractionListener,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val canSubmit =
        addEditGoalUiState.goalName.isNotBlank() &&
        addEditGoalUiState.targetAmount != null &&
        addEditGoalUiState.targetAmount > 0.0 &&
        addEditGoalUiState.deadline != null

    BottomSheetTemplate(
        title = (if (isEditing) Res.string.edit_goal else Res.string.add_new_goal).asString(),
        dismissText = Res.string.cancel.asString(),
        onDismiss = onDismiss,
        onClickAction = onSubmit,
        actionText = if (isEditing) Res.string.edit_goal.asString() else Res.string.save_goal.asString(),
        actionButtonState = when {
            isLoading -> AppButtonState.Loading
            canSubmit -> AppButtonState.Enabled
            else -> AppButtonState.Disabled
        },
        modifier = Modifier.padding(bottom = 32.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                CustomTextField(
                    value = addEditGoalUiState.goalName,
                    onValueChange = interactionListener::onGoalNameChanged,
                    hint = Res.string.enter_goal_name.asString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                CustomTextField(
                    value = addEditGoalUiState.targetAmount.toCleanString(),
                    onValueChange = { interactionListener.onTargetAmountChanged(it.toCleanDoubleOrNull()) },
                    hint = Res.string.enter_your_target_amount.asString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                CustomTextField(
                    value = addEditGoalUiState.deadline?.format() ?: "",
                    onValueChange = { },
                    hint = Res.string.enter_your_target_date.asString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            interactionListener.onShowDatePicker(true)
                        }
                        .padding(bottom = 8.dp),
                    enabled = false,
                    onTrailingIconClick = {
                        focusManager.clearFocus()
                        interactionListener.onShowDatePicker(true)
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    trailingIcon = Res.drawable.ic_date.painter(),
                    trailingIconColor = Theme.colorScheme.text.label
                )

                DatePicker(
                    showDialog = addEditGoalUiState.showDatePicker,
                    selectedDate = addEditGoalUiState.deadline,
                    onDateSelected = interactionListener::onDeadlineChanged,
                    onDismiss = { interactionListener.onShowDatePicker(false) }
                )

                Text(
                    text = Res.string.priority.asString(),
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.text.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                AppSegmentedControl(
                    options = PriorityOption.entries.map { it.toGenSelectableOption() },
                    selectedOption = addEditGoalUiState.priority,
                    onOptionSelected = interactionListener::onPriorityChanged
                )

                Text(
                    text = Res.string.icon.asString(),
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.text.titleSmall,
                    modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
                )
            }
        }

        item {
            SelectableIconRow(
                selectedIcon = addEditGoalUiState.icon,
                onIconSelected = interactionListener::onIconChanged,
                entries = CategoryIcon.entries,
                toDrawableResource = { this.toDrawableResource() }
            )
        }
    }
}

private fun PriorityOption.toGenSelectableOption(): GenSelectableOption<PriorityOption> {
    return when (this) {
        PriorityOption.LOW -> GenSelectableOption(PriorityOption.LOW, Res.string.low)
        PriorityOption.MEDIUM -> GenSelectableOption(PriorityOption.MEDIUM, Res.string.medium)
        PriorityOption.HIGH -> GenSelectableOption(PriorityOption.HIGH, Res.string.high)
    }
}
