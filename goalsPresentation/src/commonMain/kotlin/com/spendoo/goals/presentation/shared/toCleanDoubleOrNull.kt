package com.spendoo.goals.presentation.shared

fun String.toCleanDoubleOrNull(): Double? { //TODO: move to shared domain
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
