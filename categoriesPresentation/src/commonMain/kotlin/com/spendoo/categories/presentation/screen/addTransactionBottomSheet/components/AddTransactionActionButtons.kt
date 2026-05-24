package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionInteractionListener
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionUiState
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.theme.theme.Theme
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.dialogs.FileKitType
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add
import spendoo.designsystem.generated.resources.ic_camera
import spendoo.designsystem.generated.resources.ic_mic
import spendoo.designsystem.generated.resources.ic_mobile
import spendoo.designsystem.generated.resources.ic_robot

@Composable
fun BoxScope.AddTransactionActionButtons(
    state: AddTransactionUiState,
    interactionListener: AddTransactionInteractionListener,
) {
    val imagePicker = rememberFilePickerLauncher(
        type = FileKitType.Image,
        onResult = interactionListener::onSelectImage
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(Theme.colorScheme.background.tertiary)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SpendooIconButton(
            onClick = { imagePicker.launch() }, //TODO: camera or gallery
            iconRes = Res.drawable.ic_camera,
            tint = Theme.colorScheme.icon.primary,
            backgroundColor = Theme.colorScheme.button.secondary,
            contentDescription = null,
            showBorder = false,
            iconSize = 24.dp,
            size = 56.dp,
        )
        SpendooIconButton(
            onClick = { /* TODO: Voice logic */ },
            iconRes = Res.drawable.ic_mic,
            tint = Theme.colorScheme.icon.primary,
            backgroundColor = Theme.colorScheme.button.secondary,
            contentDescription = null,
            showBorder = false,
            iconSize = 24.dp,
            size = 56.dp,
        )
        AppButton(
            type = AppButtonType.Primary,
            text = stringResource(Res.string.add),
            onClick = { interactionListener.submit() },
            state = if (state.isSubmitting) AppButtonState.Loading else AppButtonState.Enabled,
            modifier = Modifier.weight(1f).height(56.dp)
        )
    }
}