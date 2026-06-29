package com.spendoo.statistics.presentation.screen.statistics.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.spendoo.designsystem.components.general.SelectableOptionRowColumn
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.statistics.presentation.screen.statistics.TransactionSortOption
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.select
import spendoo.designsystem.generated.resources.sorting_options

@Composable
fun TransactionSortSheet(
    show: Boolean,
    selectedOption: TransactionSortOption,
    onOptionSelected: (TransactionSortOption) -> Unit,
    onDismiss: () -> Unit
) {
    BottomSheet(
        isVisible = show,
        onDismiss = onDismiss
    ) {
        TransactionSortContent(
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
            onDismiss = onDismiss
        )
    }
}

@Composable
fun TransactionSortContent(
    selectedOption: TransactionSortOption,
    onOptionSelected: (TransactionSortOption) -> Unit,
    onDismiss: () -> Unit
) {
    BottomSheetTemplate(
        title = Res.string.sorting_options.asString(),
        dismissText = Res.string.cancel.asString(),
        showDividers = false,
        showActionButtons = false,
        onDismiss = onDismiss,
        onClickAction = { },
        actionText = Res.string.select.asString(),
    ) {
        item {
            SelectableOptionRowColumn(
                options = TransactionSortOption.entries,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected,
                getName = { this.title }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TransactionSortContentPreview() {
    SpendooTheme {
        TransactionSortContent(
            selectedOption = TransactionSortOption.DATE_DESCENDING,
            onOptionSelected = {},
            onDismiss = {}
        )
    }
}
