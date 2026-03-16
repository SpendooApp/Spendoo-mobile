package com.spendoo.identity.presentation.screen.onboarding

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class OnboardingUiState(
    val pages: List<OnboardingPageUiState> = emptyList(),
    val currentPageIndex: Int = 0,
    val isLastPage: Boolean = false
)

data class OnboardingPageUiState(
    val title: StringResource,
    val description: StringResource,
    val imageRes: DrawableResource
)
