package com.spendoo.designsystem.utils

import kotlin.time.Duration

fun formatDuration(duration: Duration): String {
    return duration.toComponents { hours, minutes, seconds, _ ->
        if (hours > 0) {
            "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
        } else {
            "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
        }
    }
}
