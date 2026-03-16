package com.spendoo.identity.presentation.screen.onboarding

import com.spendoo.identity.presentation.navigation.LoginRoute
import com.spendoo.identity.presentation.shared.BaseViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_dollar
import spendoo.designsystem.generated.resources.ic_done
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.ic_target
import spendoo.designsystem.generated.resources.ic_wallet
import spendoo.designsystem.generated.resources.onboarding_desc_insights
import spendoo.designsystem.generated.resources.onboarding_desc_ready
import spendoo.designsystem.generated.resources.onboarding_desc_set_goals
import spendoo.designsystem.generated.resources.onboarding_desc_track_spending
import spendoo.designsystem.generated.resources.onboarding_desc_welcome
import spendoo.designsystem.generated.resources.onboarding_title_insights
import spendoo.designsystem.generated.resources.onboarding_title_ready
import spendoo.designsystem.generated.resources.onboarding_title_set_goals
import spendoo.designsystem.generated.resources.onboarding_title_track_spending
import spendoo.designsystem.generated.resources.onboarding_title_welcome

class OnboardingViewModel : BaseViewModel<OnboardingUiState>(OnboardingUiState()), OnboardingInteractionListener {

    init {
        updateState {
            copy(
                pages = listOf(
                    OnboardingPageUiState(
                        title = Res.string.onboarding_title_welcome,
                        description = Res.string.onboarding_desc_welcome,
                        imageRes = Res.drawable.ic_wallet
                    ),
                    OnboardingPageUiState(
                        title = Res.string.onboarding_title_track_spending,
                        description = Res.string.onboarding_desc_track_spending,
                        imageRes = Res.drawable.ic_dollar
                    ),
                    OnboardingPageUiState(
                        title = Res.string.onboarding_title_set_goals,
                        description = Res.string.onboarding_desc_set_goals,
                        imageRes = Res.drawable.ic_target
                    ),
                    OnboardingPageUiState(
                        title = Res.string.onboarding_title_insights,
                        description = Res.string.onboarding_desc_insights,
                        imageRes = Res.drawable.ic_stats
                    ),
                    OnboardingPageUiState(
                        title = Res.string.onboarding_title_ready,
                        description = Res.string.onboarding_desc_ready,
                        imageRes = Res.drawable.ic_done
                    )
                )
            )
        }
    }

    override fun onNextButtonClicked() {
        val currentState = state.value
        if (currentState.currentPageIndex < currentState.pages.size - 1) {
            val nextIndex = currentState.currentPageIndex + 1
            updateState {
                copy(
                    currentPageIndex = nextIndex,
                    isLastPage = nextIndex == pages.size - 1
                )
            }
        } else {
            navigate(LoginRoute)
        }
    }

    override fun onPageSelected(position: Int) {
        updateState {
            copy(
                currentPageIndex = position,
                isLastPage = position == pages.size - 1
            )
        }
    }

    override fun onSkipButtonClicked() {
        navigate(LoginRoute)
    }

    override fun onPreviousButtonClicked() {
        val currentState = state.value
        if (currentState.currentPageIndex > 0) {
            val prevIndex = currentState.currentPageIndex - 1
            updateState {
                copy(
                    currentPageIndex = prevIndex,
                    isLastPage = false
                )
            }
        }
    }
}
