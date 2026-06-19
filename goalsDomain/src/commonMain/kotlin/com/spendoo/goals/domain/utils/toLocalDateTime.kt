package com.spendoo.goals.domain.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

fun LocalDate.toLocalDateTime(): LocalDateTime { //TODO: move to shared domain
    return this.atTime(0, 0)
}
