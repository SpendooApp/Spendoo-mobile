package com.spendoo.goals.presentation.screen.goals.components

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
import com.spendoo.shared.domain.utils.toCleanDoubleOrNull
import com.spendoo.shared.domain.utils.toCleanString
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.extentions.asString
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add_to_goal_title
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.enter_your_amount
import spendoo.designsystem.generated.resources.confirm

data class AddAmountUiState(
    val amount: Double? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null
)

@Composable
fun AddAmountToGoalBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    initialAmount: Double?,
    goalName: String,
    onAddAmount: (Double) -> Unit,
    isLoading: Boolean,
    errorText: String? = null,
    onAmountChanged: () -> Unit = {}
) {
    var amountText: Double? by remember(initialAmount, isVisible) { mutableStateOf(initialAmount) }

    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
        horizontalPadding = 0.dp
    ) {
        BottomSheetTemplate(
            title = Res.string.add_to_goal_title.asString(goalName),
            dismissText = Res.string.cancel.asString(),
            showDividers = false,
            onDismiss = onDismiss,
            onClickAction = {
                amountText?.let { onAddAmount(it) }
            },
            actionText = Res.string.confirm.asString(),
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
                        onAmountChanged()
                    },
                    hint = Res.string.enter_your_amount.asString(),
                    errorText = errorText,
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
