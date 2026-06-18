package com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet

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
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components.SelectableRow
import com.spendoo.categories.presentation.screen.categorySelectionSheet.CategorySelectionSheet
import com.spendoo.categories.presentation.shared.getToday
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.dialog.DatePicker
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
        state.title.isNotBlank() && state.amount.isNotBlank() && state.categoryId.isNotBlank()

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
                    trailingIcon = Res.drawable.ic_arrow_down.painter(),
                    trailingIconColor = Theme.colorScheme.text.link
                )

                CustomTextField(
                    value = (state.startDate ?: getToday()).format(),
                    onValueChange = { },
                    hint = Res.string.enter_start_date.asString(),
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            interactionListener.onShowDatePicker(true)
                        }
                        .padding(bottom = 16.dp),
                    trailingIcon = Res.drawable.ic_date.painter(),
                    trailingIconColor = Theme.colorScheme.text.label
                )

                DatePicker(
                    showDialog = state.showDatePicker,
                    selectedDate = state.startDate,
                    onDateSelected = interactionListener::onStartDateChanged,
                    onDismiss = { interactionListener.onShowDatePicker(false) }
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )

                    // Dropdown simulation for Unit
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
                }
            }
        }
    }
}
