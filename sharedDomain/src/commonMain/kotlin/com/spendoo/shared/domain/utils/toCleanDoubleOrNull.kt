package com.spendoo.shared.domain.utils

fun String.toCleanDoubleOrNull(): Double? {
    val trimmed = this.trim()
    if (trimmed.isEmpty()) return null

    val withoutDecimals = if (trimmed.contains('.')) {
        trimmed.substringBefore('.')
    } else {
        trimmed
    }

    val numericOnly = withoutDecimals.filterIndexed { index, char ->
        char.isDigit() || (index == 0 && char == '-')
    }

    return numericOnly.toDoubleOrNull()
}
