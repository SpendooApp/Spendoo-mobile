package com.spendoo.goals.domain.entity

enum class GoalIcon {
    DEFAULT, FOOD, TRANSPORT, ENTERTAINMENT, HEALTHCARE, EDUCATION, SHOPPING,
    TRAVEL, CAR, MOBILE, FINANCE, COFFEE, GIFTS, PETS, FITNESS, UTILITIES, WIFI;

    companion object {
        fun fromStringOrDefault(iconName: String?): GoalIcon {
            return iconName?.let {
                entries.find { it.name == iconName }
            } ?: DEFAULT
        }
    }
}
