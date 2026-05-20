package com.spendoo.categories.domain.entity.category

enum class ResetCycleOption { DAILY, WEEKLY, MONTHLY, YEARLY }

fun Int.toResetCycleOption(): ResetCycleOption = when (this) {
    1 -> ResetCycleOption.DAILY
    7 -> ResetCycleOption.WEEKLY
    30 -> ResetCycleOption.MONTHLY
    365 -> ResetCycleOption.YEARLY
    else -> ResetCycleOption.MONTHLY
}

fun ResetCycleOption.toInt(): Int = when (this) {
    ResetCycleOption.DAILY -> 1
    ResetCycleOption.WEEKLY -> 7
    ResetCycleOption.MONTHLY -> 30
    ResetCycleOption.YEARLY -> 365
}