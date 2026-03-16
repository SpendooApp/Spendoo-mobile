package com.spendoo.identity.presentation.screen.onboarding

interface OnboardingInteractionListener {
    fun onNextButtonClicked()
    fun onPageSelected(position: Int)
    fun onSkipButtonClicked()
    fun onPreviousButtonClicked()
}