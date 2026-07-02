package com.spendoo.categories.presentation.screen.financialActionScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.dialog.AppAlertContent
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.utils.extentions.asString
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.ic_cancel
import spendoo.designsystem.generated.resources.ok

@Composable
fun FinancialActionScreen(
    tile: String,
    body: String,
    payload: Map<String, String>,
    viewModel: FinancialActionViewModel =
        koinViewModel(parameters = { parametersOf(tile, body, payload) })
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BottomSheet(
        isVisible = true,
        onDismiss = viewModel::onDismiss,
        canSwipeToDismiss = false,
        horizontalPadding = 0.dp,
        skipPartiallyExpanded = false
    ) {
        FinancialActionBottomSheetContent(
            state = state,
            interactionListener = viewModel
        )
    }
}

@Composable
fun FinancialActionBottomSheetContent(
    state: FinancialActionUiState,
    interactionListener: FinancialActionInteractionListener
) {
    AppAlertContent(
        modifier = Modifier
            .fillMaxWidth(),
        iconRes = Res.drawable.ic_cancel,
        title = state.tile,
        minWidth = Dp.Unspecified,
        shape = RectangleShape,
        description = state.body,
        actionText = Res.string.ok.asString(),
        onActionClick = {},
        dismissText = Res.string.cancel.asString(),
        onDismissRequest = interactionListener::onDismiss
    )
}