package com.spendoo.goals.data.mapper

import kotlinx.datetime.LocalDateTime

fun String.toLocalDateTimeOrDefault(): LocalDateTime { //TODO: move to shared domain
    return runCatching {
        LocalDateTime.parse(this)
    }.getOrElse {
        LocalDateTime(1999, 1, 1, 0, 0)
    }
}
