package com.spendoo.categories.presentation.screen.inputVoiceBottomSheet

import kotlin.time.Duration

data class InputVoiceUiState(
    val showPermissionDialog: Boolean = false,
    val timerDuration: Duration = Duration.ZERO,
    val recordedAmplitudes: List<Float> = emptyList(),
    val playbackProgress: Float = 0f,
)