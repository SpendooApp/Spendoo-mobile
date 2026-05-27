package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun AudioWave(
    recordWave: List<Float>,
    modifier: Modifier = Modifier,
    listenRatio: Float = 1f,
    height: Dp = 150.dp,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val ratioIndex = (listenRatio * recordWave.size).toInt()
        recordWave.forEachIndexed { index, wave ->
            val isActive = index < ratioIndex
            val targetColor = if (isActive) {
                Theme.colorScheme.additional.blue
            } else {
                Theme.colorScheme.additional.blue.copy(alpha = 0.3f)
            }
            val animatedColor = animateColorAsState(targetColor, label = "waveColor")
            val targetHeight = (height * wave).coerceAtLeast(15.dp)
            val animatedHeight = animateDpAsState(targetHeight, label = "waveHeight")

            Box(
                modifier = Modifier
                    .width(15.dp)
                    .height(animatedHeight.value)
                    .background(animatedColor.value, RoundedCornerShape(100))
            )
        }
    }
}

@Composable
@PreviewLightDark
fun AudioWavePreview() = SpendooTheme {
    AudioWave(
        recordWave = listOf(
            0.1f,
            0.5f,
            0.3f,
            0.7f,
            0.2f,
            0.9f,
            0.4f,
            0.6f,
            0.8f,
            0.2f,
            0.5f,
            0.3f,
            0.1f,
            0.5f,
            0.3f,
            0.7f,
            0.2f,
            0.9f,
            0.4f,
            0.6f,
            0.8f,
            0.2f,
            0.5f,
            0.3f,
        ),
        listenRatio = 0.75f,
    )
}

@Composable
@PreviewLightDark
fun AudioWavePreview2() = SpendooTheme {
    AudioWave(
        recordWave = listOf(
            0.1f,
            0.5f,
            0.3f,
            0.7f,
            0.2f,
            0.9f,
            0.4f,
            0.6f,
            0.8f,
            0.2f,
            0.5f,
            0.3f
        ),
        listenRatio = 0.5f,
    )
}