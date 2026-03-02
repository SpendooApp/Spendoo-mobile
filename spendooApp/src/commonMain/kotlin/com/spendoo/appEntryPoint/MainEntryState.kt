package com.spendoo.appEntryPoint

data class MainEntryState(
    val activeFeature: Feature = Feature.Home,
    var showBottomNavigation: Boolean = true,
)

enum class Feature {
    Home, Categories, Stats, ChatBot, PROFILE, Payments
}