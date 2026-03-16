package com.spendoo.appEntryPoint

data class MainEntryState(
    val activeFeature: Feature = Feature.Home,
    val showBottomNavigation: Boolean = true,
)

enum class Feature {
    Home, Categories, Stats, ChatBot, Profile, Payments
}