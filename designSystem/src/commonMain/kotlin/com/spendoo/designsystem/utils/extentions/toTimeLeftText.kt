package com.spendoo.designsystem.utils.extentions

import com.spendoo.designsystem.utils.UiText
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.overdue
import spendoo.designsystem.generated.resources.today
import spendoo.designsystem.generated.resources.tomorrow
import spendoo.designsystem.generated.resources.days_left
import spendoo.designsystem.generated.resources.hour_left
import spendoo.designsystem.generated.resources.hours_left
import spendoo.designsystem.generated.resources.week_left
import spendoo.designsystem.generated.resources.weeks_left
import spendoo.designsystem.generated.resources.month_left
import spendoo.designsystem.generated.resources.months_left
import spendoo.designsystem.generated.resources.year_left
import spendoo.designsystem.generated.resources.years_left

@OptIn(ExperimentalTime::class)
fun LocalDateTime.toTimeLeftText(
    now: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
): UiText {
    val dueInstant = this.toInstant(TimeZone.currentSystemDefault())
    val nowInstant = now.toInstant(TimeZone.currentSystemDefault())
    val duration = dueInstant - nowInstant

    if (duration.isNegative()) {
        return UiText.StringRes(Res.string.overdue)
    }

    val days = duration.inWholeDays
    return when {
        days >= 365L -> {
            val years = days / 365L
            if (years == 1L) UiText.StringRes(Res.string.year_left)
            else UiText.StringRes(Res.string.years_left, years.toString())
        }
        days >= 30L -> {
            val months = days / 30L
            if (months == 1L) UiText.StringRes(Res.string.month_left)
            else UiText.StringRes(Res.string.months_left, months.toString())
        }
        days >= 7L -> {
            val weeks = days / 7L
            if (weeks == 1L) UiText.StringRes(Res.string.week_left)
            else UiText.StringRes(Res.string.weeks_left, weeks.toString())
        }
        days >= 2L -> {
            UiText.StringRes(Res.string.days_left, days.toString())
        }
        days == 1L -> {
            UiText.StringRes(Res.string.tomorrow)
        }
        else -> { // days == 0
            val hours = duration.inWholeHours
            when {
                hours > 1L -> UiText.StringRes(Res.string.hours_left, hours.toString())
                hours == 1L -> UiText.StringRes(Res.string.hour_left)
                else -> UiText.StringRes(Res.string.today)
            }
        }
    }
}
