package com.spendoo.identity.presentation.screen.onboarding

import com.spendoo.identity.presentation.navigation.LoginRoute
import com.spendoo.identity.presentation.shared.BaseViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_dollar
import spendoo.designsystem.generated.resources.ic_done
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.ic_target
import spendoo.designsystem.generated.resources.ic_wallet

class OnboardingViewModel : BaseViewModel<OnboardingUiState>(OnboardingUiState()), OnboardingInteractionListener {

    init {
        updateState {
            copy(
                pages = listOf(
                    OnboardingPageUiState(
                        title = "Welcome to SPENDoo",
                        description = "Your smart companion for managing finances and achieving your financial goals effortlessly.",
                        imageRes = Res.drawable.ic_wallet
                    ),
                    OnboardingPageUiState(
                        title = "Track Your Spending",
                        description = "Monitor every transaction and categorize your expenses automatically. Stay on top of where your money goes.",
                        imageRes = Res.drawable.ic_dollar
                    ),
                    OnboardingPageUiState(
                        title = "Set Financial Goals",
                        description = "Create savings goals for the things you love. Track your progress and celebrate milestones along the way.",
                        imageRes = Res.drawable.ic_target
                    ),
                    OnboardingPageUiState(
                        title = "Get Smart Insights",
                        description = "Visualize your spending patterns with beautiful charts and get personalized recommendations to save more.",
                        imageRes = Res.drawable.ic_stats
                    ),
                    OnboardingPageUiState(
                        title = "Ready to Start?",
                        description = "Join thousands of users who are taking control of their finances and building better money habits.",
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
