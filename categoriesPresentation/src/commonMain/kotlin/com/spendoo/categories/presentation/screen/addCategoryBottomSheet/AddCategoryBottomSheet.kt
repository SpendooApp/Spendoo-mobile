package com.spendoo.categories.presentation.screen.addCategoryBottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.LiftoverFundsActionSheet
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.SelectableIconRow
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.SelectableResetCycleRow
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.SelectedPriorityRow
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.SpendooPreview
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.format
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add_category
import spendoo.designsystem.generated.resources.add_new_category
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.edit_category
import spendoo.designsystem.generated.resources.enter_budget
import spendoo.designsystem.generated.resources.enter_budget_start_date
import spendoo.designsystem.generated.resources.enter_category_name
import spendoo.designsystem.generated.resources.ic_arrow_down
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.icon
import spendoo.designsystem.generated.resources.leftover_funds_action
import spendoo.designsystem.generated.resources.priority
import spendoo.designsystem.generated.resources.reset_budget_cycle

@Composable
fun AddCategoryBottomSheet(
    isVisible: Boolean,
    initialAddCategoryUiState: AddCategoryUiState? = null,
    onDismiss: () -> Unit,
    onAddCategory: (AddCategoryUiState) -> Unit,
) {
    var addCategoryUiState by remember {
        mutableStateOf(
            initialAddCategoryUiState ?: AddCategoryUiState()
        )
    }

    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
    ) {
        AddCategoryContent(
            isEditing = initialAddCategoryUiState != null,
            addCategoryUiState = addCategoryUiState,
            onAddCategoryUiStateChange = { addCategoryUiState = it },
            onDismiss = onDismiss,
            onSubmit = { onAddCategory(addCategoryUiState) }
        )
    }
}

@Composable
private fun AddCategoryContent(
    isEditing: Boolean = false,
    addCategoryUiState: AddCategoryUiState,
    onAddCategoryUiStateChange: (AddCategoryUiState) -> Unit,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    BottomSheetTemplate(
        title = (if (isEditing) Res.string.edit_category else Res.string.add_new_category).asString(),
        dismissText = Res.string.cancel.asString(),
        onDismiss = onDismiss,
        onClickAction = onSubmit,
        actionText = Res.string.add_category.asString(),
        modifier = Modifier.padding(bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CustomTextField(
                value = addCategoryUiState.categoryName,
                onValueChange = { onAddCategoryUiStateChange(addCategoryUiState.copy(categoryName = it)) },
                hint = Res.string.enter_category_name.asString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            CustomTextField(
                value = addCategoryUiState.budget?.toString().orEmpty(),
                onValueChange = {
                    val budgetValue = it.toIntOrNull() ?: 0
                    onAddCategoryUiStateChange(addCategoryUiState.copy(budget = budgetValue))
                },
                hint = Res.string.enter_budget.asString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            CustomTextField(
                value = addCategoryUiState.budgetStartDate.format(),
                onValueChange = {  },
                hint = Res.string.enter_budget_start_date.asString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableNoRipple {
                        focusManager.clearFocus()
                        onAddCategoryUiStateChange(addCategoryUiState.copy(showDatePicker = true))
                    }
                    .padding(bottom = 8.dp),
                enabled = false,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                trailingIcon = Res.drawable.ic_date.painter(),
                trailingIconColor = Theme.colorScheme.text.label
            )

            DatePicker(
                showDialog = addCategoryUiState.showDatePicker,
                selectedDate = addCategoryUiState.budgetStartDate,
                onDateSelected = {
                    onAddCategoryUiStateChange(
                        addCategoryUiState.copy(
                            budgetStartDate = it,
                            showDatePicker = false
                        )
                    )
                },
                onDismiss = { onAddCategoryUiStateChange(addCategoryUiState.copy(showDatePicker = false)) }
            )

            CustomTextField(
                value = addCategoryUiState.leftoverFundsAction?.toStringResource()?.asString()
                    .orEmpty(),
                onValueChange = { },
                hint = Res.string.leftover_funds_action.asString(),
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableNoRipple {
                        focusManager.clearFocus()
                        onAddCategoryUiStateChange(
                            addCategoryUiState.copy(
                                showLeftoverFundsActionSheet = true
                            )
                        )
                    }
                    .padding(bottom = 14.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                trailingIcon = Res.drawable.ic_arrow_down.painter(),
                trailingIconColor = Theme.colorScheme.text.link
            )

            LiftoverFundsActionSheet(
                show = addCategoryUiState.showLeftoverFundsActionSheet,
                initialSelectedOption = addCategoryUiState.leftoverFundsAction,
                onOptionSelected = { option ->
                    onAddCategoryUiStateChange(
                        addCategoryUiState.copy(
                            leftoverFundsAction = option,
                            showLeftoverFundsActionSheet = false
                        )
                    )
                },
                onDismiss = {
                    onAddCategoryUiStateChange(
                        addCategoryUiState.copy(
                            showLeftoverFundsActionSheet = false
                        )
                    )
                }
            )

            Text(
                text = Res.string.priority.asString(),
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.text.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            SelectedPriorityRow(
                selectedPriority = addCategoryUiState.priority,
                onPrioritySelected = { priority ->
                    onAddCategoryUiStateChange(addCategoryUiState.copy(priority = priority))
                }
            )

            Text(
                text = Res.string.icon.asString(),
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.text.titleSmall,
                modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
            )
        }

        SelectableIconRow(
            selectedIcon = addCategoryUiState.icon,
            onIconSelected = { icon ->
                onAddCategoryUiStateChange(addCategoryUiState.copy(icon = icon))
            }
        )

        Text(
            text = Res.string.reset_budget_cycle.asString(),
            style = Theme.typography.label.medium.small,
            color = Theme.colorScheme.text.titleSmall,
            modifier = Modifier.padding(start = 16.dp, top = 14.dp, bottom = 8.dp)
        )

        SelectableResetCycleRow(
            selectedCycle = addCategoryUiState.resetCycle,
            onCycleSelected = { cycle ->
                onAddCategoryUiStateChange(addCategoryUiState.copy(resetCycle = cycle))
            }
        )
    }
}


@Preview(widthDp = 400)
@Composable
private fun AddCategoryScreenPreview() = SpendooPreview {
    var addCategoryUiState by remember {
        mutableStateOf(
            AddCategoryUiState()
        )
    }
    AddCategoryContent(
        addCategoryUiState = addCategoryUiState,
        onAddCategoryUiStateChange = {
            addCategoryUiState = it
        },
        onDismiss = {},
        onSubmit = {}
    )
}
