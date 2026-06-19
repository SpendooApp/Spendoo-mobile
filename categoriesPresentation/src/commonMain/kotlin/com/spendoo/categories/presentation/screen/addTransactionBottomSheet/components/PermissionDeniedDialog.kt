package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.runtime.Composable
import com.spendoo.designsystem.components.dialog.AppAlert
import com.spendoo.designsystem.utils.extentions.asString
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.go_to_settings
import spendoo.designsystem.generated.resources.img_microphone_record
import spendoo.designsystem.generated.resources.microphone_permission_denied
import spendoo.designsystem.generated.resources.microphone_permission_is_required

@Composable
fun PermissionDeniedDialog(
    onDismiss: () -> Unit,
    onGoToSettings: () -> Unit
) {
    AppAlert(
        iconRes = Res.drawable.img_microphone_record,
        title = Res.string.microphone_permission_denied.asString(),
        description = Res.string.microphone_permission_is_required.asString(),
        actionText = Res.string.go_to_settings.asString(),
        onActionClick = onGoToSettings,
        dismissText = Res.string.cancel.asString(),
        onDismissRequest = onDismiss
    )
}