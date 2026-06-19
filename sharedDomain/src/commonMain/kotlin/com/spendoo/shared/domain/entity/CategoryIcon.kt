package com.spendoo.shared.domain.entity

enum class CategoryIcon {
    DEFAULT,
    FOOD,
    TRANSPORT,
    ENTERTAINMENT,
    HEALTHCARE,
    EDUCATION,
    SHOPPING,
    TRAVEL,
    CAR,
    MOBILE,
    FINANCE,
    COFFEE,
    GIFTS,
    PETS,
    FITNESS,
    UTILITIES,
    WIFI;

    companion object {
        fun fromStringOrDefault(iconName: String?): CategoryIcon {
            return iconName?.let {
                entries.find { it.name == iconName }
            } ?: DEFAULT
        }
    }
}