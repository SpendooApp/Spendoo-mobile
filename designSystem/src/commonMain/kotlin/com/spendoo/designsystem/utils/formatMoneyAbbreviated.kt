package com.spendoo.designsystem.utils

import kotlin.math.abs
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.thousand_abbreviation
import spendoo.designsystem.generated.resources.million_abbreviation
import spendoo.designsystem.generated.resources.billion_abbreviation

fun formatMoneyAbbreviated(amount: Double): UiText {
    val absAmount = abs(amount)
    return when {
        absAmount >= 1_000_000_000.0 -> {
            val value = amount / 1_000_000_000.0
            UiText.StringRes(Res.string.billion_abbreviation, value.formatOneDecimal())
        }
        absAmount >= 1_000_000.0 -> {
            val value = amount / 1_000_000.0
            UiText.StringRes(Res.string.million_abbreviation, value.formatOneDecimal())
        }
        absAmount >= 1_000.0 -> {
            val value = amount / 1_000.0
            UiText.StringRes(Res.string.thousand_abbreviation, value.formatOneDecimal())
        }
        else -> UiText.DynamicString(formatMoney(amount))
    }
}

private fun Double.formatOneDecimal(): String {
    val integerPart = this.toInt()
    val decimalPart = ((this - integerPart) * 10).toInt()
    val absoluteDecimal = if (decimalPart < 0) -decimalPart else decimalPart
    return if (absoluteDecimal == 0) {
        "$integerPart"
    } else {
        "$integerPart.$absoluteDecimal"
    }
}
