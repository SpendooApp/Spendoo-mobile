package com.spendoo.notifications.domain.entity

enum class NotificationType {
    ACHIEVEMENT,
    GOAL,
    USER_FOLLOW,
    PAYMENT_REMINDER,
    ALERT,
    SYSTEM,
    TRACKING_REMINDER;

    companion object {
        fun fromStringOrDefault(value: String?): NotificationType {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: SYSTEM
        }
    }
}
