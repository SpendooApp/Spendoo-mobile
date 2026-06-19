package com.spendoo.identity.presentation.screen.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.identity.presentation.screen.onboarding.OnboardingPageUiState
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun OnboardingPager(
    pages: List<OnboardingPageUiState>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth(),
    ) { pageIndex ->
        val page = pages[pageIndex]

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 40.dp)
                    .size(256.dp)
                    .background(
                        color = Theme.colorScheme.background.senary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(page.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }

            Text(
                text = page.title.asString(),
                style = Theme.typography.heading.large,
                color = Theme.colorScheme.text.headingBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            Text(
                text = page.description.asString(),
                style = Theme.typography.body.medium,
                color = Theme.colorScheme.text.bodyBlue,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun OnboardingPagerPreview() = SpendooTheme {
    val pagerState = rememberPagerState { 5 }
    OnboardingPager(
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
        ),
        pagerState = pagerState,
    )
}

