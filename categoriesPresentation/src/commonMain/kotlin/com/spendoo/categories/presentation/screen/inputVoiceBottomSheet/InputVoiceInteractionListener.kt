package com.spendoo.categories.presentation.screen.inputVoiceBottomSheet

import kotlin.time.Duration

interface InputVoiceInteractionListener {
    fun onShowPermissionDialog(show: Boolean)
    fun onTimerDurationChanged(duration: Duration)
    fun onRecordedAmplitudesChanged(amplitudes: List<Float>)
    fun onPlaybackProgressChanged(progress: Float)
    fun reset()
    fun startTimer()
    fun stopTimer()
    fun startPlaybackProgress(totalDurationMillis: Long)
    fun stopPlaybackProgress()
}