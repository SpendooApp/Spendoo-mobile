package com.spendoo.categories.presentation.shared

fun String.toCleanDoubleOrNull(): Double? {
    val trimmed = this.trim()
    if (trimmed.isEmpty()) return null

    // 1. If there's a dot, ignore everything from the dot onward
    val withoutDecimals = if (trimmed.contains('.')) {
        trimmed.substringBefore('.')
    } else {
        trimmed
    }

    // 2. Keep only the valid numeric digits (and a leading minus sign if negative)
    // This strips out any stray accidental letters or special characters
    val numericOnly = withoutDecimals.filterIndexed { index, char ->
        char.isDigit() || (index == 0 && char == '-')
    }

    // 3. Convert the clean whole number string straight to a Double
    return numericOnly.toDoubleOrNull()
}