package com.spendoo.categories.presentation.shared

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getToday(): LocalDate {
    return Clock.System.now().toLocalDateTime(currentSystemDefault()).date
}