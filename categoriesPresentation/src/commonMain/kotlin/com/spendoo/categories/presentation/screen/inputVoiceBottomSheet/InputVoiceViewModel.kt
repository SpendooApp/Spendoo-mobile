package com.spendoo.categories.presentation.screen.inputVoiceBottomSheet

import androidx.lifecycle.viewModelScope
import com.spendoo.designsystem.navigation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration

class InputVoiceViewModel : BaseViewModel<InputVoiceUiState>(InputVoiceUiState()),
    InputVoiceInteractionListener {

    private var timerJob: Job? = null
    private var playbackJob: Job? = null

    override fun onShowPermissionDialog(show: Boolean) {
        updateState { it.copy(showPermissionDialog = show) }
    }

    override fun onTimerDurationChanged(duration: Duration) {
        updateState { it.copy(timerDuration = duration) }
    }

    override fun onRecordedAmplitudesChanged(amplitudes: List<Float>) {
        updateState { it.copy(recordedAmplitudes = amplitudes) }
    }

    override fun onPlaybackProgressChanged(progress: Float) {
        updateState { it.copy(playbackProgress = progress) }
    }

    override fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            val startMoment = Clock.System.now()
            val initialDuration = state.value.timerDuration
            while (isActive) {
                updateState { it.copy(timerDuration = initialDuration + (Clock.System.now() - startMoment)) }
                delay(100)
            }
        }
    }

    override fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun startPlaybackProgress(totalDurationMillis: Long) {
        playbackJob?.cancel()
        playbackJob = viewModelScope.launch {
            val startMoment = Clock.System.now()
            val startProgress = state.value.playbackProgress

            while (isActive) {
                val elapsed = (Clock.System.now() - startMoment).inWholeMilliseconds
                val progress = (startProgress + (elapsed.toFloat() / totalDurationMillis)).coerceIn(0f, 1f)
                updateState { it.copy(playbackProgress = progress) }
                if (progress >= 1f) break
                delay(16)
            }
        }
    }

    override fun stopPlaybackProgress() {
        playbackJob?.cancel()
        playbackJob = null
    }

    override fun reset() {
        stopTimer()
        stopPlaybackProgress()
        updateState { InputVoiceUiState() }
    }
}
