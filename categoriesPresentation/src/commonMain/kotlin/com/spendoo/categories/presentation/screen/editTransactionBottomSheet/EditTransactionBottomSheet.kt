package com.spendoo.categories.presentation.screen.editTransactionBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.domain.entity.transaction.TransactionType
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.categories.presentation.screen.categorySelectionSheet.CategorySelectionSheet
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.components.dialog.TimePicker
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.asString
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.format
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.shared.domain.utils.toCleanString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.amount
import spendoo.designsystem.generated.resources.choose_category
import spendoo.designsystem.generated.resources.enter_your_date
import spendoo.designsystem.generated.resources.ic_arrow_down
import spendoo.designsystem.generated.resources.ic_clock_red
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.note_optional
import spendoo.designsystem.generated.resources.save
import spendoo.designsystem.generated.resources.time_label
import spendoo.designsystem.generated.resources.title

@Composable
fun EditTransactionBottomSheet(
    transactionId: String,
    viewModel: EditTransactionViewModel = koinViewModel(parameters = { parametersOf(transactionId) })
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    BottomSheet(
        isVisible = true,
        skipPartiallyExpanded = true,
        horizontalPadding = 0.dp,
        onDismiss = viewModel::onSheetHidden
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = viewModel::onTitleChanged,
                hint = Res.string.title.asString(),
                textStyle = Theme.typography.body.medium,
                errorText = state.titleError?.asString()
            )
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.amount.toCleanString(),
                onValueChange = viewModel::onAmountChanged,
                hint = Res.string.amount.asString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = Theme.typography.body.medium,
                errorText = state.amountError?.asString()
            )
            if (state.transactionType == TransactionType.EXPENSE) {
                CustomTextField(
                    value = state.categoryName,
                    onValueChange = { },
                    hint = stringResource(Res.string.choose_category),
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            viewModel.onCategoryFieldClicked()
                        },
                    onTrailingIconClick = {
                        focusManager.clearFocus()
                        viewModel.onCategoryFieldClicked()
                    },
                    trailingIcon = Res.drawable.ic_arrow_down.painter(),
                    trailingIconColor = Theme.colorScheme.text.label,
                    prefix = state.categoryIcon?.let { icon ->
                        {
                            Icon(
                                painter = icon.toDrawableResource().painter(),
                                contentDescription = null,
                                tint = Theme.colorScheme.button.onSecondary,
                                modifier = Modifier.size(20.dp).padding(end = 8.dp)
                            )
                        }
                    },
                    errorText = state.categoryError?.asString()
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomTextField(
                    value = state.date.format(),
                    onValueChange = { },
                    hint = stringResource(Res.string.enter_your_date),
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            viewModel.onDatePickerRequested()
                        },
                    onTrailingIconClick = {
                        focusManager.clearFocus()
                        viewModel.onDatePickerRequested()
                    },
                    trailingIcon = Res.drawable.ic_date.painter(),
                    trailingIconColor = Theme.colorScheme.text.label
                )
                CustomTextField(
                    value = state.time.format(),
                    onValueChange = { },
                    hint = stringResource(Res.string.time_label),
                    enabled = false,
                    modifier = Modifier
                        .weight(1f)
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            viewModel.onTimePickerRequested()
                        },
                    onTrailingIconClick = {
                        focusManager.clearFocus()
                        viewModel.onTimePickerRequested()
                    },
                    trailingIcon = Res.drawable.ic_clock_red.painter(),
                    trailingIconColor = Theme.colorScheme.text.label
                )
            }
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.note,
                onValueChange = viewModel::onNoteChanged,
                hint = Res.string.note_optional.asString(),
                textStyle = Theme.typography.body.medium,
                errorText = state.noteError?.asString()
            )
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                type = AppButtonType.Primary,
                text = Res.string.save.asString(),
                onClick = viewModel::saveTransaction,
                state = if (state.isSaving) AppButtonState.Loading else AppButtonState.Enabled
            )
        }
    }

    CategorySelectionSheet(
        isVisible = state.showCategorySheet,
        onCategorySelected = viewModel::onCategorySelected,
        onDismiss = viewModel::onCategorySheetDismissed
    )

    DatePicker(
        showDialog = state.showDatePicker,
        onDismiss = viewModel::onDatePickerDismissed,
        onDateSelected = viewModel::onDateSelected,
        selectedDate = state.date
    )

    TimePicker(
        showDialog = state.showTimePicker,
        onDismiss = viewModel::onTimePickerDismissed,
        onTimeSelected = viewModel::onTimeSelected,
        selectedTime = state.time
    )
}
