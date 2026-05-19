package com.spendoo.categories.presentation.screen.addCategoryBottomSheet.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toSelectableOptions
import com.spendoo.designsystem.components.general.SelectableOptionRowColumn
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.utils.extentions.asString
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.leftover_funds_action
import spendoo.designsystem.generated.resources.select

@Composable
fun LiftoverFundsActionSheet(
    show: Boolean,
    initialSelectedOption: LeftOverOption,
    onOptionSelected: (LeftOverOption) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedOption by remember { mutableStateOf(initialSelectedOption) }

    BottomSheet(
        isVisible = show,
        onDismiss = onDismiss
    ){
        BottomSheetTemplate(
            title = Res.string.leftover_funds_action.asString(),
            dismissText = Res.string.cancel.asString(),
            showDividers = false,
            onDismiss = onDismiss,
            onClickAction = { onOptionSelected(selectedOption) },
            actionText = Res.string.select.asString(),
        ) {
            item {
                SelectableOptionRowColumn(
                    options = LeftOverOption.entries.toSelectableOptions(),
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it },
                )
            }
        }
    }
}
