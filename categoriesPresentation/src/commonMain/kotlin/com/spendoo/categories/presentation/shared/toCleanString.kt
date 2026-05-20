package com.spendoo.categories.presentation.shared

fun Double?.toCleanString(): String {
    // If the Double is null, return an empty string
    if (this == null) return ""
    
    // If it's a whole number, cast to Long to instantly remove ".0"
    if (this % 1.0 == 0.0) {
        return this.toLong().toString()
    }
    
    val str = this.toString()
    
    // Handle scientific notation (e.g., 1.23E-4 or 1.23E4) if present
    if (str.contains('E', ignoreCase = true)) {
        return handleScientificNotation(str)
    }
    
    return str
}

// Helper to expand scientific notation cleanly across platforms
private fun handleScientificNotation(str: String): String {
    val parts = str.split(Regex("[eE]"))
    val coefficient = parts[0]
    val exponent = parts[1].toInt()
    
    val dotIndex = coefficient.indexOf('.')
    val pureDigits = coefficient.replace(".", "")
    
    return if (exponent < 0) {
        val moveLeft = -exponent
        val zeroCount = moveLeft - if (dotIndex != -1) dotIndex else coefficient.length
        "0." + "0".repeat(zeroCount.coerceAtLeast(0)) + pureDigits
    } else {
        val existingDecimals = if (dotIndex != -1) coefficient.length - 1 - dotIndex else 0
        if (exponent >= existingDecimals) {
            pureDigits + "0".repeat(exponent - existingDecimals)
        } else {
            val newDotPos = (if (dotIndex != -1) dotIndex else coefficient.length) + exponent
            pureDigits.substring(0, newDotPos) + "." + pureDigits.substring(newDotPos)
        }
    }
}