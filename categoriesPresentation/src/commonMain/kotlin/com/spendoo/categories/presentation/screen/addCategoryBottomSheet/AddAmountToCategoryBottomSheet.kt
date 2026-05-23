package com.spendoo.categories.presentation.screen.addCategoryBottomSheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.spendoo.categories.presentation.shared.toCleanDoubleOrNull
import com.spendoo.categories.presentation.shared.toCleanString
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.utils.extentions.asString
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add
import spendoo.designsystem.generated.resources.add_amount_to
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.enter_budget

@Composable
fun AddAmountToCategoryBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    initialAmount: Double?,
    categoryName: String,
    onAddAmount: (Double) -> Unit,
    isLoading: Boolean
) {
    var amountText: Double? by remember(initialAmount, isVisible) { mutableStateOf(initialAmount) }

    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        horizontalPadding = 0.dp
    ) {
        BottomSheetTemplate(
            title = Res.string.add_amount_to.asString(categoryName),
            dismissText = Res.string.cancel.asString(),
            showDividers = false,
            onDismiss = onDismiss,
            onClickAction = {
                amountText?.let { onAddAmount(it) }
            },
            actionText = Res.string.add.asString(),
            actionButtonState = when {
                isLoading -> AppButtonState.Loading
                amountText != null && amountText!! > 0 -> AppButtonState.Enabled
                else -> AppButtonState.Disabled
            }
        ) {

            item {
                CustomTextField(
                    value = amountText.toCleanString(),
                    onValueChange = { newValue ->
                        amountText = newValue.toCleanDoubleOrNull()
                    },
                    hint = Res.string.enter_budget.asString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
        }
    }
}