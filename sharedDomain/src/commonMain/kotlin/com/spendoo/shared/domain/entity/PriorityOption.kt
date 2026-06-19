package com.spendoo.shared.domain.entity

enum class PriorityOption { LOW, MEDIUM, HIGH }

fun Int.toPriorityOption(): PriorityOption = when (this) {
    0 -> PriorityOption.LOW
    1 -> PriorityOption.MEDIUM
    2 -> PriorityOption.HIGH
    else -> PriorityOption.MEDIUM
}