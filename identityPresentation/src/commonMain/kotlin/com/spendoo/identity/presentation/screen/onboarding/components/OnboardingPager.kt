package com.spendoo.identity.presentation.screen.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.spendoo.identity.presentation.screen.onboarding.OnboardingPageUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_wallet

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
                text = page.title,
                style = Theme.typography.heading.large,
                color = Theme.colorScheme.text.headingBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            Text(
                text = page.description,
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
    val pagerState = rememberPagerState { 1 }
    OnboardingPager(
        pages = listOf(
            OnboardingPageUiState(
                title = "Title",
                description = "Description",
                imageRes = Res.drawable.ic_wallet
            )
        ),
        pagerState = pagerState,
    )
}