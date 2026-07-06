package com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.designsystem.components.row.SelectableRow
import com.spendoo.categories.presentation.screen.categorySelectionSheet.CategorySelectionSheet
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.enter_custom_days
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.components.dialog.TimePicker
import com.spendoo.designsystem.components.dropdownMenu.DropdownMenu
import com.spendoo.designsystem.components.dropdownMenu.DropdownMenuItem
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.components.surface.Surface
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.format
import com.spendoo.designsystem.utils.extentions.painter
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add_payment
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.choose_category
import spendoo.designsystem.generated.resources.edit_payment
import spendoo.designsystem.generated.resources.enter_amount
import spendoo.designsystem.generated.resources.enter_period
import spendoo.designsystem.generated.resources.enter_start_date
import spendoo.designsystem.generated.resources.enter_title
import spendoo.designsystem.generated.resources.frequency
import spendoo.designsystem.generated.resources.ic_arrow_down
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.ic_clock_red
import spendoo.designsystem.generated.resources.time_label
import spendoo.designsystem.generated.resources.reminder_period
import spendoo.designsystem.generated.resources.save_payment

@Composable
fun AddScheduledPaymentBottomSheet(
    isVisible: Boolean,
    initialState: AddScheduledPaymentUiState?,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: AddScheduledPaymentViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(isVisible, initialState) {
        if (isVisible) {
            viewModel.init(initialState)
        }
    }

    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        skipPartiallyExpanded = true,
        horizontalPadding = 0.dp
    ) {
        AddScheduledPaymentContent(
            state = state,
            interactionListener = viewModel,
            onDismiss = onDismiss,
            onSubmit = {
                viewModel.onSubmit(onSuccess = onSuccess)
            }
        )
    }
}

@Composable
private fun AddScheduledPaymentContent(
    state: AddScheduledPaymentUiState,
    interactionListener: AddScheduledPaymentInteractionListener,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val canSubmit =
        state.title.isNotBlank() && 
        state.amount.isNotBlank() && 
        state.categoryId.isNotBlank() &&
        (state.frequency != PaymentFrequency.CUSTOM || (state.customFrequencyDays.isNotBlank() && state.customFrequencyDaysError == null)) &&
        state.reminderPeriodValueError == null

    BottomSheetTemplate(
        title = (if (state.isEditing) Res.string.edit_payment else Res.string.add_payment).asString(),
        dismissText = Res.string.cancel.asString(),
        onDismiss = onDismiss,
        onClickAction = onSubmit,
        actionText = Res.string.save_payment.asString(),
        actionButtonState = when {
            state.isLoading -> AppButtonState.Loading
            canSubmit -> AppButtonState.Enabled
            else -> AppButtonState.Disabled
        },
    ) {
        item {
            CategorySelectionSheet(
                isVisible = state.isCategorySelectionSheetVisible,
                onDismiss = { interactionListener.onShowCategorySelectionSheet(false) },
                onCategorySelected = interactionListener::onCategorySelected
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                CustomTextField(
                    value = state.title,
                    onValueChange = interactionListener::onTitleChanged,
                    hint = Res.string.enter_title.asString(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp, top = 4.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                CustomTextField(
                    value = state.amount,
                    onValueChange = interactionListener::onAmountChanged,
                    hint = Res.string.enter_amount.asString(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )

                CustomTextField(
                    value = state.categoryName,
                    onValueChange = { },
                    hint = Res.string.choose_category.asString(),
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            interactionListener.onShowCategorySelectionSheet(true)
                        }
                        .padding(bottom = 8.dp),
                    onTrailingIconClick = {
                        focusManager.clearFocus()
                        interactionListener.onShowCategorySelectionSheet(true)
                    },
                    trailingIcon = Res.drawable.ic_arrow_down.painter(),
                    trailingIconColor = Theme.colorScheme.text.link
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomTextField(
                        value = state.startDate.date.format(),
                        onValueChange = { },
                        hint = Res.string.enter_start_date.asString(),
                        enabled = false,
                        modifier = Modifier
                            .weight(1f)
                            .clickableNoRipple {
                                focusManager.clearFocus()
                                interactionListener.onShowDatePicker(true)
                            },
                        onTrailingIconClick = {
                            focusManager.clearFocus()
                            interactionListener.onShowDatePicker(true)
                        },
                        trailingIcon = Res.drawable.ic_date.painter(),
                        trailingIconColor = Theme.colorScheme.text.label
                    )
                    CustomTextField(
                        value = state.startDate.time.format(),
                        onValueChange = { },
                        hint = stringResource(Res.string.time_label),
                        enabled = false,
                        modifier = Modifier
                            .weight(1f)
                            .clickableNoRipple {
                                focusManager.clearFocus()
                                interactionListener.onShowTimePicker(true)
                            },
                        onTrailingIconClick = {
                            focusManager.clearFocus()
                            interactionListener.onShowTimePicker(true)
                        },
                        trailingIcon = Res.drawable.ic_clock_red.painter(),
                        trailingIconColor = Theme.colorScheme.text.label
                    )
                }

                DatePicker(
                    showDialog = state.showDatePicker,
                    selectedDate = state.startDate.date,
                    onDateSelected = interactionListener::onStartDateChanged,
                    onDismiss = { interactionListener.onShowDatePicker(false) }
                )

                TimePicker(
                    showDialog = state.showTimePicker,
                    selectedTime = state.startDate.time,
                    onTimeSelected = interactionListener::onStartTimeChanged,
                    onDismiss = { interactionListener.onShowTimePicker(false) }
                )

                Text(
                    text = Res.string.frequency.asString(),
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.text.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        item {
            SelectableRow(
                selectedCycle = state.frequency,
                entries = PaymentFrequency.entries,
                getName = { it.toText().asString() },
                onCycleSelected = interactionListener::onFrequencyChanged
            )
        }

        if (state.frequency == PaymentFrequency.CUSTOM) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    CustomTextField(
                        value = state.customFrequencyDays,
                        onValueChange = interactionListener::onCustomFrequencyDaysChanged,
                        hint = stringResource(Res.string.enter_custom_days),
                        modifier = Modifier.fillMaxWidth(),
                        errorText = state.customFrequencyDaysError?.asString(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 14.dp)
            ) {
                Text(
                    text = Res.string.reminder_period.asString(),
                    style = Theme.typography.label.medium.small,
                    color = Theme.colorScheme.text.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CustomTextField(
                        value = state.reminderPeriodValue,
                        onValueChange = interactionListener::onReminderPeriodValueChanged,
                        hint = Res.string.enter_period.asString(),
                        modifier = Modifier.weight(1f),
                        errorText = state.reminderPeriodValueError?.asString(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )

                    Box(modifier = Modifier.wrapContentSize()) {
                        Surface(
                            onClick = {
                                focusManager.clearFocus()
                                interactionListener.onShowReminderUnitDropdown(true)
                            },
                            shape = RoundedCornerShape(12.dp),
                            color = Theme.colorScheme.button.secondary,
                            modifier = Modifier.height(56.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val unitText = state.reminderPeriodUnit.toText().asString()

                                Text(
                                    text = unitText,
                                    style = Theme.typography.label.medium.medium,
                                    color = Theme.colorScheme.text.title
                                )
                                Icon(
                                    painter = Res.drawable.ic_arrow_down.painter(),
                                    contentDescription = null,
                                    tint = Theme.colorScheme.text.link
                                )
                            }
                        }

                        val customDays = state.customFrequencyDays.toIntOrNull()
                        val availableUnits = remember(state.frequency, customDays) {
                            getAvailableReminderUnits(state.frequency, customDays)
                        }

                        DropdownMenu(
                            expanded = state.showReminderUnitDropdown,
                            onDismissRequest = { interactionListener.onShowReminderUnitDropdown(false) },
                            modifier = Modifier.background(Theme.colorScheme.background.secondary)
                        ) {
                            availableUnits.forEach { unit ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = unit.toText().asString(),
                                            style = Theme.typography.body.medium,
                                            color = Theme.colorScheme.text.title
                                        )
                                    },
                                    onClick = {
                                        interactionListener.onReminderPeriodUnitChanged(unit)
                                        interactionListener.onShowReminderUnitDropdown(false)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
