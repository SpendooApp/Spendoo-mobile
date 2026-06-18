package com.spendoo.categories.presentation.screen.addCategoryBottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.PriorityOption
import com.spendoo.categories.domain.entity.category.ResetCycleOption
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.LiftoverFundsActionSheet
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.SelectableIconRow
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.SelectableRow
import com.spendoo.categories.presentation.shared.getToday
import com.spendoo.categories.presentation.shared.toCleanDoubleOrNull
import com.spendoo.categories.presentation.shared.toCleanString
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.components.general.AppSegmentedControl
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
import kotlinx.datetime.LocalDate
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
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
import spendoo.designsystem.generated.resources.leave_it_blank_or_0_for_no_budget_limit
import spendoo.designsystem.generated.resources.leftover_funds_action
import spendoo.designsystem.generated.resources.priority
import spendoo.designsystem.generated.resources.reset_budget_cycle

@Composable
fun AddEditCategoryBottomSheet(
    isVisible: Boolean,
    initialAddEditCategoryUiState: AddEditCategoryUiState?,
    onDismiss: () -> Unit,
    onAddCategory: (AddEditCategoryUiState) -> Unit,
    viewModel: AddCategoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(isVisible, initialAddEditCategoryUiState) {
        if (isVisible) {
            viewModel.init(initialAddEditCategoryUiState)
        }
    }

    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        horizontalPadding = 0.dp
    ) {
        AddEditCategoryContent(
            isEditing = state.categoryId != null,
            isLoading = state.isLoading,
            addEditCategoryUiState = state,
            interactionListener = viewModel,
            onDismiss = onDismiss,
            onSubmit = {
                viewModel.submit(onSuccess = onAddCategory)
            }
        )
    }
}

@Composable
private fun AddEditCategoryContent(
    isEditing: Boolean,
    addEditCategoryUiState: AddEditCategoryUiState,
    interactionListener: AddCategoryInteractionListener,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val canSubmit =
        addEditCategoryUiState.categoryName.isNotBlank()

    BottomSheetTemplate(
        title = (if (isEditing) Res.string.edit_category else Res.string.add_new_category).asString(),
        dismissText = Res.string.cancel.asString(),
        onDismiss = onDismiss,
        onClickAction = onSubmit,
        actionText = if (isEditing) Res.string.edit_category.asString() else Res.string.add_category.asString(),
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
                    value = addEditCategoryUiState.categoryName,
                    onValueChange = interactionListener::onCategoryNameChanged,
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
                    value = addEditCategoryUiState.budget.toCleanString(),
                    onValueChange = { interactionListener.onBudgetChanged(it.toCleanDoubleOrNull()) },
                    hint = Res.string.enter_budget.asString(),
                    helperText = Res.string.leave_it_blank_or_0_for_no_budget_limit.asString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                CustomTextField(
                    value = (addEditCategoryUiState.budgetStartDate ?: getToday()).format(),
                    onValueChange = { },
                    hint = Res.string.enter_budget_start_date.asString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            interactionListener.onShowDatePicker(true)
                        }
                        .padding(bottom = 8.dp),
                    enabled = false,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    trailingIcon = Res.drawable.ic_date.painter(),
                    trailingIconColor = Theme.colorScheme.text.label
                )

                DatePicker(
                    showDialog = addEditCategoryUiState.showDatePicker,
                    selectedDate = addEditCategoryUiState.budgetStartDate,
                    onDateSelected = interactionListener::onBudgetStartDateChanged,
                    onDismiss = { interactionListener.onShowDatePicker(false) }
                )

                CustomTextField(
                    value = addEditCategoryUiState.leftoverFundsAction.toStringResource()
                        .asString(),
                    onValueChange = { },
                    hint = Res.string.leftover_funds_action.asString(),
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            interactionListener.onShowLeftoverFundsActionSheet(true)
                        }
                        .padding(bottom = 14.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    trailingIcon = Res.drawable.ic_arrow_down.painter(),
                    trailingIconColor = Theme.colorScheme.text.link
                )

                LiftoverFundsActionSheet(
                    show = addEditCategoryUiState.showLeftoverFundsActionSheet,
                    initialSelectedOption = addEditCategoryUiState.leftoverFundsAction,
                    onOptionSelected = interactionListener::onLeftoverFundsActionChanged,
                    onDismiss = { interactionListener.onShowLeftoverFundsActionSheet(false) }
                )

                Text(
                    text = Res.string.priority.asString(),
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.text.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                AppSegmentedControl(
                    options = PriorityOption.entries.map { it.toGenSelectableOption() },
                    selectedOption = addEditCategoryUiState.priority,
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
                selectedIcon = addEditCategoryUiState.icon,
                onIconSelected = interactionListener::onIconChanged
            )
        }

        item {
            Text(
                text = Res.string.reset_budget_cycle.asString(),
                style = Theme.typography.label.medium.small,
                color = Theme.colorScheme.text.titleSmall,
                modifier = Modifier.padding(start = 16.dp, top = 14.dp, bottom = 8.dp)
            )
        }

        item {
            SelectableRow(
                selectedCycle = addEditCategoryUiState.resetCycle,
                entries = ResetCycleOption.entries,
                getName = { it.toStringResource().asString() },
                onCycleSelected = interactionListener::onResetCycleChanged
            )
        }
    }
}

@Preview(widthDp = 400)
@Composable
private fun AddCategoryScreenPreview() = SpendooPreview {
    var addEditCategoryUiState by remember {
        mutableStateOf(
            AddEditCategoryUiState()
        )
    }
    AddEditCategoryContent(
        addEditCategoryUiState = addEditCategoryUiState,
        interactionListener = object : AddCategoryInteractionListener {
            override fun onCategoryNameChanged(name: String) {}
            override fun onBudgetChanged(budget: Double?) {}
            override fun onBudgetStartDateChanged(date: LocalDate) {}
            override fun onLeftoverFundsActionChanged(action: LeftOverOption) {}
            override fun onPriorityChanged(priority: PriorityOption) {}
            override fun onIconChanged(icon: CategoryIcon) {}
            override fun onResetCycleChanged(cycle: ResetCycleOption) {}
            override fun onShowDatePicker(show: Boolean) {}
            override fun onShowLeftoverFundsActionSheet(show: Boolean) {}
            override fun submit(onSuccess: (AddEditCategoryUiState) -> Unit) {}
        },
        onDismiss = {},
        onSubmit = {},
        isEditing = false,
        isLoading = false
    )
}


