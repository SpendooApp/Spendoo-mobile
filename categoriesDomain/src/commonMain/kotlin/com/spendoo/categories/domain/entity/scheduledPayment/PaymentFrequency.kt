package com.spendoo.categories.domain.entity.scheduledPayment

enum class PaymentFrequency {
    DAILY, WEEKLY, MONTHLY, YEARLY, CUSTOM;

    fun toDays(): Int {
        return when (this) {
            DAILY -> 1
            WEEKLY -> 7
            MONTHLY -> 30
            YEARLY -> 365
            CUSTOM -> 0
        }
    }

    companion object {
        fun fromDays(days: Int): PaymentFrequency {
            return when (days) {
                1 -> DAILY
                7 -> WEEKLY
                30 -> MONTHLY
                365 -> YEARLY
                else -> CUSTOM
            }
        }
    }
}
