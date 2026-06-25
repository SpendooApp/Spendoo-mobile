package com.spendoo.designsystem.utils

fun formatMoney(amount: Double): String {
    val integerPart = amount.toInt()
    val cents = ((amount - integerPart) * 100).toInt()
    val absoluteCents = if (cents < 0) -cents else cents
    val centsString = absoluteCents.toString().padStart(2, '0')
    return "$integerPart.$centsString"
}
