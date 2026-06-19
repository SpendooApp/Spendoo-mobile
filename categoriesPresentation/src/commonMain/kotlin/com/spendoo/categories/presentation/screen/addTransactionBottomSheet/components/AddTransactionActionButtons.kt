package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.animation.AnimatedContent
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
import com.spendoo.categories.presentation.screen.inputVoiceBottomSheet.InputVoiceBottomSheet
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionInteractionListener
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.AddTransactionUiState
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.TransactionType
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberCameraPickerLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.add
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.ic_camera
import spendoo.designsystem.generated.resources.ic_gallery
import spendoo.designsystem.generated.resources.ic_mic

@Composable
fun BoxScope.AddTransactionActionButtons(
    state: AddTransactionUiState,
    onDismiss: () -> Unit,
    interactionListener: AddTransactionInteractionListener,
) {
    val imagePicker = rememberFilePickerLauncher(
        type = FileKitType.Image,
        onResult = interactionListener::onSelectImage
    )

    val cameraPicker = rememberCameraPickerLauncher(
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
        AnimatedContent(
            targetState = state.type,
            label = "MediaButtonsAnimation",
        ) { type ->
            if (type == TransactionType.Expense) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SpendooIconButton(
                        onClick = { cameraPicker.launch() },
                        iconRes = Res.drawable.ic_camera,
                        tint = Theme.colorScheme.icon.primary,
                        backgroundColor = Theme.colorScheme.button.secondary,
                        contentDescription = null,
                        showBorder = false,
                        iconSize = 24.dp,
                        size = 56.dp,
                        enabled = !state.isProcessingMedia
                    )
                    SpendooIconButton(
                        onClick = { imagePicker.launch() },
                        iconRes = Res.drawable.ic_gallery,
                        tint = Theme.colorScheme.icon.primary,
                        backgroundColor = Theme.colorScheme.button.secondary,
                        contentDescription = null,
                        showBorder = false,
                        iconSize = 24.dp,
                        size = 56.dp,
                        enabled = !state.isProcessingMedia
                    )
                    SpendooIconButton(
                        onClick = {
                            interactionListener.setAudioRecordingVisibility(true)
                        },
                        iconRes = Res.drawable.ic_mic,
                        tint = Theme.colorScheme.icon.primary,
                        backgroundColor = Theme.colorScheme.button.secondary,
                        contentDescription = null,
                        showBorder = false,
                        iconSize = 24.dp,
                        size = 56.dp,
                        enabled = !state.isProcessingMedia
                    )
                }
            }
            else {
                AppButton(
                    modifier = Modifier.weight(1f),
                    type = AppButtonType.Secondary,
                    onClick = onDismiss,
                    text = Res.string.cancel.asString(),
                )
            }
        }
        AppButton(
            type = AppButtonType.Primary,
            text = stringResource(Res.string.add),
            onClick = { interactionListener.submit() },
            state = if (state.isSubmitting || state.isProcessingMedia) AppButtonState.Loading else AppButtonState.Enabled,
            modifier = Modifier.weight(1f).height(56.dp)
        )
    }

    InputVoiceBottomSheet(
        isVisible = state.showAudioPicker,
        onDismiss = { interactionListener.setAudioRecordingVisibility(false) },
        onRecordingComplete = { audio ->
            interactionListener.onVoiceProcessed(audio)
            interactionListener.setAudioRecordingVisibility(false)
        }
    )
}