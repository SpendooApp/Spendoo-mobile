package com.spendoo.identity.presentation.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.button.TextButton
import com.spendoo.designsystem.components.indicator.InteractivePagerIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.util.extentions.asString
import com.spendoo.identity.presentation.screen.onboarding.components.BottomArrows
import com.spendoo.identity.presentation.screen.onboarding.components.OnboardingPager
import com.spendoo.identity.presentation.shared.ScreenTemplate
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_dollar
import spendoo.designsystem.generated.resources.ic_done
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.ic_target
import spendoo.designsystem.generated.resources.ic_wallet
import spendoo.designsystem.generated.resources.skip

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OnboardingScreenContent(
        state = state,
        interactionListener = viewModel
    )
}

@Composable
fun OnboardingScreenContent(
    state: OnboardingUiState,
    interactionListener: OnboardingInteractionListener
) {
    val pagerState =
        rememberPagerState(initialPage = state.currentPageIndex, pageCount = { state.pages.size })

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        interactionListener.onPageSelected(position = pagerState.currentPage)
    }

    ScreenTemplate(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!state.isLastPage) {
                    TextButton(onClick = interactionListener::onSkipButtonClicked) {
                        Text(
                            text = Res.string.skip.asString(),
                            style = Theme.typography.title.small,
                            color = Theme.colorScheme.primary.variant600
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                OnboardingPager(
                    pages = state.pages,
                    pagerState = pagerState,
                )
                InteractivePagerIndicator(
                    pagerState = pagerState,
                )
            }

            BottomArrows(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp, start = 16.dp, end = 16.dp, top = 30.dp),
                isLastPage = state.isLastPage,
                isFirstPage = state.currentPageIndex == 0,
                onNextButtonClicked = {
                    interactionListener.onNextButtonClicked()
                    coroutineScope.launch {
                        if (pagerState.currentPage < state.pages.lastIndex) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                onPreviousButtonClicked = {
                    interactionListener.onPreviousButtonClicked()
                    coroutineScope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }
            )
        }
    }
}


@Composable
@Preview(heightDp = 800)
fun OnboardingScreenPreview() = SpendooTheme {
    OnboardingScreenContent(
        state = OnboardingUiState(
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
            ),
            currentPageIndex = 0,
            isLastPage = false
        ),
        interactionListener = object : OnboardingInteractionListener {
            override fun onNextButtonClicked() {}
            override fun onPageSelected(position: Int) {}
            override fun onSkipButtonClicked() {}
            override fun onPreviousButtonClicked() {}
        }
    )
}
