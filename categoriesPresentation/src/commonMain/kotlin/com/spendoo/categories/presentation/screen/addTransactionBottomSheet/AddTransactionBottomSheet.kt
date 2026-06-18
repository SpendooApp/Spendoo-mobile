package com.spendoo.categories.presentation.screen.addTransactionBottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components.AddExpenseEntriesSection
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components.AddTransactionActionButtons
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components.IncomeEntriesSection
import com.spendoo.categories.presentation.screen.categorySelectionSheet.CategorySelectionSheet
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.components.general.AppSegmentedControl
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.SpendooPreview
import com.spendoo.designsystem.utils.extentions.format
import com.spendoo.designsystem.utils.extentions.painter
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add
import spendoo.designsystem.generated.resources.add_transaction
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.enter_your_date
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.write_a_note_optional

@Composable
fun AddTransactionBottomSheet(
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BottomSheet(
        isVisible = true,
        onDismiss = viewModel::onDismiss,
        horizontalPadding = 0.dp,
        skipPartiallyExpanded = true
    ) {
        AddTransactionBottomSheetContent(
            state = state,
            onDismiss = viewModel::onDismiss,
            interactionListener = viewModel
        )
    }

    CategorySelectionSheet(
        isVisible = state.showCategorySheet,
        onCategorySelected = viewModel::onCategorySelected,
        onDismiss = viewModel::onCategorySheetDismissed,
    )

    DatePicker(
        showDialog = state.showDatePicker,
        selectedDate = state.date,
        onDateSelected = viewModel::onDateSelected,
        onDismiss = viewModel::onDatePickerDismissed
    )
}

@Composable
private fun AddTransactionBottomSheetContent(
    state: AddTransactionUiState,
    onDismiss: () -> Unit,
    interactionListener: AddTransactionInteractionListener
) {
    val focusManager = LocalFocusManager.current

    Box {
        BottomSheetTemplate(
            title = stringResource(Res.string.add_transaction),
            dismissText = stringResource(Res.string.cancel),
            onDismiss = onDismiss,
            onClickAction = interactionListener::submit,
            actionText = stringResource(Res.string.add),
            showActionButtons = false,
            modifier = Modifier.padding(bottom = 80.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AppSegmentedControl(
                        options = state.transactionTypeOptions,
                        selectedOption = state.type,
                        onOptionSelected = interactionListener::onTypeSelected,
                        modifier = Modifier.padding(bottom = 24.dp, top = 8.dp)
                            .padding(horizontal = 16.dp)
                    )

                    AddExpenseEntriesSection(
                        state = state,
                        interactionListener = interactionListener
                    )

                    IncomeEntriesSection(state = state, interactionListener = interactionListener)

                    AnimatedVisibility(
                        visible = !state.isSavingChecked,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        CustomTextField(
                            value = state.date.format(),
                            onValueChange = { },
                            hint = stringResource(Res.string.enter_your_date),
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickableNoRipple {
                                    focusManager.clearFocus()
                                    interactionListener.onDatePickerRequested()
                                }
                                .padding(bottom = 8.dp)
                                .padding(horizontal = 16.dp),
                            trailingIcon = Res.drawable.ic_date.painter(),
                            trailingIconColor = Theme.colorScheme.text.label
                        )
                    }

                    AnimatedVisibility(
                        visible = state.type == TransactionType.Income && !state.isSavingChecked,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        CustomTextField(
                            value = state.incomeNote,
                            onValueChange = interactionListener::onNoteChanged,
                            hint = stringResource(Res.string.write_a_note_optional),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .padding(horizontal = 16.dp),
                            singleLine = false,
                            minLines = 3,
                            errorText = state.incomeNoteError
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.padding(bottom = 24.dp))
            }
        }

        AddTransactionActionButtons(
            state = state,
            onDismiss = onDismiss,
            interactionListener = interactionListener,
        )
    }
}


val previewInteractionListener = object : AddTransactionInteractionListener {
    override fun onSheetHidden() {}
    override fun onDismiss() {}
    override fun onTypeSelected(type: TransactionType) {}
    override fun onCategoryFieldClicked(entryId: String) {}
    override fun onCategorySelected(category: CategoryItemUiState) {}
    override fun onCategorySheetDismissed() {}
    override fun onVoiceProcessed(file: ByteArray) {}
    override fun onSelectImage(file: PlatformFile?) {}
    override fun removeExpenseEntry(id: String) {}
    override fun onEntryChanged(id: String, entry: TransactionEntryUiState) {}
    override fun addExpenseEntry() {}
    override fun onIncomeTitleChanged(title: String) {}
    override fun onIncomeAmountChanged(amount: String) {}
    override fun onSavingChecked(checked: Boolean) {}
    override fun onSavingAmountChanged(amount: String) {}
    override fun onDatePickerRequested() {}
    override fun onDatePickerDismissed() {}
    override fun onNoteChanged(note: String) {}
    override fun onDateSelected(date: LocalDate) {}
    override fun submit() {}
    override fun setAudioRecordingVisibility(bool: Boolean) {}
}

@Composable
@Preview
private fun AddTransactionBottomSheetPreview() = SpendooPreview {
    AddTransactionBottomSheetContent(
        state = AddTransactionUiState(
            type = TransactionType.Expense,
            expenseEntries = listOf(
                TransactionEntryUiState(
                    id = "1",
                    title = "Groceries",
                    amount = 50.00,
                    categoryId = "c1",
                    categoryName = "Food",
                    categoryIcon = CategoryIcon.FOOD,
                ),
                TransactionEntryUiState(
                    id = "2",
                    title = "Transport",
                    amount = 20.00,
                    categoryId = "c2",
                    categoryName = "Transport",
                    categoryIcon = CategoryIcon.MOBILE,
                )
            )
        ),
        onDismiss = {},
        interactionListener = previewInteractionListener
    )
}



