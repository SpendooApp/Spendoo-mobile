package com.spendoo.identity.presentation.screen.onboarding

import org.jetbrains.compose.resources.DrawableResource

data class OnboardingUiState(
    val pages: List<OnboardingPageUiState> = emptyList(),
    val currentPageIndex: Int = 0,
    val isLastPage: Boolean = false
)

data class OnboardingPageUiState(
    val title: String,
    val description: String,
    val imageRes: DrawableResource
)
