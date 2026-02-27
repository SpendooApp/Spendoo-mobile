package com.spendoo.appEntryPoint

interface MainEntryInteractionListener {
    fun onBottomNavigationChanged(isShowed: Boolean)
    fun setActiveFeature(feature: Feature)
}