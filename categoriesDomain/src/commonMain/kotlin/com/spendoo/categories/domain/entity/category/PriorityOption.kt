package com.spendoo.categories.domain.entity.category

enum class PriorityOption { LOW, MEDIUM, HIGH }

fun Int.toPriorityOption(): PriorityOption = when (this) {
    0 -> PriorityOption.LOW
    1 -> PriorityOption.MEDIUM
    2 -> PriorityOption.HIGH
    else -> PriorityOption.MEDIUM
}