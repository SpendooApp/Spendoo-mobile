package com.spendoo.categories.presentation.screen.inputVoiceBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.presentation.openAppSettings
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components.PermissionDeniedDialog
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.sheet.BottomSheetTemplate
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.formatDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.koin.compose.viewmodel.koinViewModel
import space.kodio.compose.AudioWaveform
import space.kodio.compose.PlayerState
import space.kodio.compose.RecorderState
import space.kodio.compose.WaveformColors
import space.kodio.compose.WaveformStyle
import space.kodio.compose.rememberPlayerState
import space.kodio.compose.rememberRecorderState
import space.kodio.core.security.AudioPermissionManager
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_cancel
import spendoo.designsystem.generated.resources.ic_delete
import spendoo.designsystem.generated.resources.ic_done_mark
import spendoo.designsystem.generated.resources.ic_mic_pause
import spendoo.designsystem.generated.resources.ic_mic_play
import spendoo.designsystem.generated.resources.ic_mic_record
import spendoo.designsystem.generated.resources.ic_pause
import spendoo.designsystem.generated.resources.ic_play
import spendoo.designsystem.generated.resources.ic_stop
import kotlin.time.Duration

@Composable
fun InputVoiceBottomSheet(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    viewModel: InputVoiceViewModel = koinViewModel(),
    onDismiss: () -> Unit,
    onRecordingComplete: (ByteArray) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val recorderState = rememberRecorderState()
    val playerState = rememberPlayerState()

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            recorderState.resetAsync()
            recorderState.stopAsync()
            playerState.stop()
            viewModel.reset()
        }
    }

    BottomSheet(
        isVisible = isVisible,
        onDismiss = {
            scope.launch {
                recorderState.resetAsync()
                recorderState.stopAsync()
                playerState.stop()
                viewModel.reset()
                onDismiss()
            }
        },
        skipPartiallyExpanded = true,
    ) {
        InputVoiceContent(
            state = state,
            recorderState = recorderState,
            playerState = playerState,
            onDismiss = {
                scope.launch {
                    recorderState.resetAsync()
                    recorderState.stopAsync()
                    playerState.stop()
                    viewModel.reset()
                    onDismiss()
                }
            },
            onRecordingComplete = onRecordingComplete,
            interactionListener = viewModel,
            modifier = modifier
        )
    }
}

@Composable
private fun InputVoiceContent(
    state: InputVoiceUiState,
    recorderState: RecorderState,
    playerState: PlayerState,
    interactionListener: InputVoiceInteractionListener,
    onDismiss: () -> Unit,
    onRecordingComplete: (ByteArray) -> Unit,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    // Sync recorder/player state with ViewModel
    LaunchedEffect(recorderState.liveAmplitudes) {
        if (recorderState.isRecording && recorderState.liveAmplitudes.isNotEmpty()) {
            interactionListener.onRecordedAmplitudesChanged(state.recordedAmplitudes + recorderState.liveAmplitudes.last())
        }
    }

    LaunchedEffect(recorderState.isRecording) {
        if (recorderState.isRecording && !recorderState.isPaused && state.timerDuration == Duration.ZERO) {
            interactionListener.onRecordedAmplitudesChanged(emptyList())
            interactionListener.onPlaybackProgressChanged(0f)
        }
    }

    LaunchedEffect(recorderState.isRecording, recorderState.isPaused) {
        if (recorderState.isRecording && !recorderState.isPaused) {
            interactionListener.startTimer()
        } else {
            interactionListener.stopTimer()
        }
    }

    LaunchedEffect(playerState.isPlaying) {
        if (playerState.isPlaying) {
            val totalDuration = playerState.recording?.calculatedDuration?.inWholeMilliseconds ?: 1L
            interactionListener.startPlaybackProgress(totalDuration)
        } else {
            interactionListener.stopPlaybackProgress()
        }
    }

    LaunchedEffect(playerState.isFinished) {
        if (playerState.isFinished) {
            interactionListener.onPlaybackProgressChanged(1f)
        }
    }

    LaunchedEffect(recorderState.recording) {
        recorderState.recording?.let { playerState.loadAsync(it) }
    }

    if (state.showPermissionDialog) {
        PermissionDeniedDialog(
            onDismiss = { interactionListener.onShowPermissionDialog(false) },
            onGoToSettings = {
                interactionListener.onShowPermissionDialog(false)
                openAppSettings()
            }
        )
    }

    BottomSheetTemplate(
        title = "",
        dismissText = "",
        onDismiss = {},
        onClickAction = {},
        actionText = "",
        modifier = modifier,
        showDividers = false,
        showActionButtons = false
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.Top)
            ) {
                WaveformSection(
                    recorderState = recorderState,
                    playerState = playerState,
                    state = state,
                    coroutineScope = coroutineScope,
                    interactionListener = interactionListener
                )

                ControlButtonsSection(
                    recorderState = recorderState,
                    playerState = playerState,
                    state = state,
                    coroutineScope = coroutineScope,
                    interactionListener = interactionListener,
                    onDismiss = onDismiss,
                    onRecordingComplete = onRecordingComplete
                )
            }
        }
    }
}

@Composable
private fun WaveformSection(
    recorderState: RecorderState,
    playerState: PlayerState,
    state: InputVoiceUiState,
    coroutineScope: CoroutineScope,
    interactionListener: InputVoiceInteractionListener
) {
    val amplitudes = when {
        recorderState.isRecording || recorderState.isPaused -> recorderState.liveAmplitudes
        recorderState.hasRecording -> state.recordedAmplitudes
        else -> emptyList()
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AudioWaveform(
            amplitudes = List(100) { 0f } + amplitudes,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(150.dp)
                .padding(horizontal = 24.dp),
            style = WaveformStyle.Spike(
                width = 15.dp,
                spacing = 2.dp,
            ),
            progress = when {
                playerState.isPlaying || playerState.isPaused || playerState.isFinished -> state.playbackProgress
                recorderState.isRecording || recorderState.isPaused -> 1f
                else -> 0f
            },
            colors = WaveformColors.solidColor(Theme.colorScheme.additional.blue)
        )
        if (recorderState.hasRecording && !recorderState.isBusy && playerState.isReady) {
            CircleButton(
                iconRes = if (playerState.isPlaying) Res.drawable.ic_pause else Res.drawable.ic_play,
                onClick = {
                    coroutineScope.launch {
                        if (playerState.isPlaying) {
                            playerState.pause()
                        } else {
                            if (playerState.isFinished) {
                                interactionListener.onPlaybackProgressChanged(0f)
                            }
                            playerState.playAsync()
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ControlButtonsSection(
    recorderState: RecorderState,
    playerState: PlayerState,
    state: InputVoiceUiState,
    coroutineScope: CoroutineScope,
    interactionListener: InputVoiceInteractionListener,
    onDismiss: () -> Unit,
    onRecordingComplete: (ByteArray) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isRecordingActive = recorderState.isRecording || recorderState.isPaused

            // Left Action Button
            LeftActionButton(
                recorderState = recorderState,
                coroutineScope = coroutineScope,
                playerState = playerState,
                interactionListener = interactionListener,
                onDismiss = onDismiss
            )

            // Center Recording Controls
            RecordingControls(
                isRecordingActive = isRecordingActive,
                recorderState = recorderState,
                playerState = playerState,
                state = state,
                coroutineScope = coroutineScope,
                interactionListener = interactionListener
            )

            // Right Done Button
            CircleButton(
                iconRes = Res.drawable.ic_done_mark,
                iconSize = 26.dp,
                enabled = recorderState.hasRecording || isRecordingActive,
                onClick = {
                    coroutineScope.launch {
                        if (isRecordingActive) recorderState.stopAsync()
                        playerState.stop()
                        recorderState.recording?.let { recording ->
                            onRecordingComplete(recording.toByteArray())
                        }
                        recorderState.resetAsync()
                        interactionListener.reset()
                    }
                }
            )
        }
    }
}

@Composable
private fun LeftActionButton(
    recorderState: RecorderState,
    coroutineScope: CoroutineScope,
    playerState: PlayerState,
    interactionListener: InputVoiceInteractionListener,
    onDismiss: () -> Unit
) {
    val isRecordingActive = recorderState.isRecording || recorderState.isPaused
    when {
        isRecordingActive || recorderState.isProcessing -> {
            CircleButton(
                iconRes = Res.drawable.ic_stop,
                enabled = !recorderState.isProcessing,
                onClick = { coroutineScope.launch { recorderState.stopAsync() } }
            )
        }

        recorderState.hasRecording -> {
            CircleButton(
                iconRes = Res.drawable.ic_delete,
                onClick = {
                    coroutineScope.launch {
                        playerState.stop()
                        recorderState.resetAsync()
                        interactionListener.reset()
                    }
                }
            )
        }

        else -> {
            CircleButton(
                iconRes = Res.drawable.ic_cancel,
                onClick = {
                    coroutineScope.launch {
                        playerState.stop()
                        recorderState.resetAsync()
                        interactionListener.reset()
                        onDismiss()
                    }
                }
            )
        }
    }
}

@Composable
private fun RecordingControls(
    isRecordingActive: Boolean,
    recorderState: RecorderState,
    playerState: PlayerState,
    state: InputVoiceUiState,
    coroutineScope: CoroutineScope,
    interactionListener: InputVoiceInteractionListener
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
    ) {
        if (isRecordingActive) {
            CircleButton(
                iconRes = if (recorderState.isPaused) Res.drawable.ic_mic_play else Res.drawable.ic_mic_pause,
                iconSize = 31.dp,
                onClick = {
                    coroutineScope.launch {
                        if (recorderState.isPaused) recorderState.resumeAsync() else recorderState.pauseAsync()
                    }
                }
            )
        } else {
            CircleButton(
                iconRes = Res.drawable.ic_mic_record,
                iconSize = 31.dp,
                enabled = !recorderState.hasRecording,
                onClick = {
                    coroutineScope.launch {
                        if (recorderState.needsPermission) {
                            recorderState.requestPermissionAsync()
                        }

                        if (recorderState.permissionState == AudioPermissionManager.State.Granted) {
                            interactionListener.reset()
                            recorderState.resetAsync()
                            recorderState.startAsync()
                        } else if (recorderState.permissionState == AudioPermissionManager.State.Denied) {
                            interactionListener.onShowPermissionDialog(true)
                        }
                    }
                }
            )
        }

        val displayDuration = when {
            playerState.isPlaying || playerState.isPaused || playerState.isFinished -> {
                val total = playerState.recording?.calculatedDuration ?: Duration.ZERO
                total * state.playbackProgress.toDouble()
            }

            state.timerDuration > Duration.ZERO -> state.timerDuration
            else -> recorderState.recording?.calculatedDuration ?: Duration.ZERO
        }

        Text(
            text = formatDuration(displayDuration),
            style = Theme.typography.heading.medium,
            color = Theme.colorScheme.text.title,
        )
    }
}

@Composable
private fun CircleButton(
    iconRes: DrawableResource,
    enabled: Boolean = true,
    iconSize: Dp = 24.dp,
    onClick: () -> Unit
) {
    SpendooIconButton(
        iconRes = iconRes,
        size = 60.dp,
        shape = CircleShape,
        contentDescription = null,
        tint = Theme.colorScheme.button.onTertiary,
        backgroundColor = Theme.colorScheme.button.tertiary,
        iconSize = iconSize,
        enabled = enabled,
        showBorder = false,
        onClick = onClick
    )
}

@Composable
@Preview
private fun InputVoiceBottomSheetPreview() = SpendooTheme {
    InputVoiceContent(
        state = InputVoiceUiState(),
        recorderState = rememberRecorderState(),
        playerState = rememberPlayerState(),
        interactionListener = object : InputVoiceInteractionListener {
            override fun onShowPermissionDialog(show: Boolean) {}
            override fun onTimerDurationChanged(duration: Duration) {}
            override fun onRecordedAmplitudesChanged(amplitudes: List<Float>) {}
            override fun onPlaybackProgressChanged(progress: Float) {}
            override fun reset() {}
            override fun startTimer() {}
            override fun stopTimer() {}
            override fun startPlaybackProgress(totalDuration: Long) {}
            override fun stopPlaybackProgress() {}
        },
        onDismiss = {},
        onRecordingComplete = {}
    )
}
