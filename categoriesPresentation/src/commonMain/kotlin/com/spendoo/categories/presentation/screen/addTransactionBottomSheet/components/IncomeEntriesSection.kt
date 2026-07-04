package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionInteractionListener
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionUiState
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.TransactionType
import com.spendoo.designsystem.components.checkbox.Checkbox
import com.spendoo.designsystem.components.checkbox.CheckboxColors
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.asString
import com.spendoo.shared.domain.utils.toCleanString
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add_to_saving
import spendoo.designsystem.generated.resources.enter_a_title
import spendoo.designsystem.generated.resources.enter_your_amount

@Composable
fun IncomeEntriesSection(
    state: AddTransactionUiState,
    interactionListener: AddTransactionInteractionListener
) {
    AnimatedVisibility(
        modifier = Modifier.padding(horizontal = 16.dp),
        visible = state.type == TransactionType.Income,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column {
            AnimatedVisibility(
                visible = !state.isSavingChecked,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                CustomTextField(
                    value = state.incomeTitle,
                    onValueChange = interactionListener::onIncomeTitleChanged,
                    hint = stringResource(Res.string.enter_a_title),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    enabled = !state.isSavingChecked,
                    singleLine = true,
                    errorText = state.incomeTitleError?.asString()
                )
            }
            CustomTextField(
                value = if (state.isSavingChecked) state.savingAmount.toCleanString() else state.incomeAmount.toCleanString(),
                onValueChange = {
                    if (state.isSavingChecked) interactionListener.onSavingAmountChanged(it)
                    else interactionListener.onIncomeAmountChanged(it)
                },
                hint = stringResource(Res.string.enter_your_amount),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                errorText = if (state.isSavingChecked) state.savingAmountError?.asString() else state.incomeAmountError?.asString()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickableNoRipple { interactionListener.onSavingChecked(!state.isSavingChecked) }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.add_to_saving),
                    style = Theme.typography.body.small,
                    color = Theme.colorScheme.text.label
                )
                Checkbox(
                    checked = state.isSavingChecked,
                    onCheckedChange = interactionListener::onSavingChecked,
                    colors = CheckboxColors.default(
                        checkedColor = Theme.colorScheme.button.primary,
                        uncheckedColor = Theme.colorScheme.text.label
                    )
                )
            }
        }
    }
}

