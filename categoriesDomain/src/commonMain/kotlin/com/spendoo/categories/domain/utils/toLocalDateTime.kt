package com.spendoo.categories.domain.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime

fun LocalDate.toLocalDateTime() = this.atTime(0, 0)