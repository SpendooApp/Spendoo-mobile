package com.spendoo.appEntryPoint

data class MainEntryState(
    val activeFeature: Feature = Feature.Home,
    val isFirstTimeOpen: Boolean = true,
    var showBottomNavigation: Boolean = true,
)

enum class Feature {
    Home, Categories, Stats, ChatBot, PROFILE, Payments
}