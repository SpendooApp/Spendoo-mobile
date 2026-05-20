package com.spendoo.categories.data.mapper

import kotlinx.datetime.LocalDateTime

fun String.toLocalDateTimeOrDefault(): LocalDateTime {
    return runCatching {
        LocalDateTime.parse(this)
    }.getOrElse {
        LocalDateTime(1999, 1, 1, 0, 0)
    }
}