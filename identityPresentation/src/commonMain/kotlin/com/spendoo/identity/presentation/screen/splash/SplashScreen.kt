package com.spendoo.identity.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.indicator.DotsProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.presentation.shared.ScreenBackground
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.img_spendoo_robot
import spendoo.designsystem.generated.resources.splash_tagline

@Composable
fun SplashScreen() {
    SplashScreenContent()
}

@Composable
private fun SplashScreenContent() {
    ScreenBackground(
        modifier = Modifier.fillMaxSize()
    ) {
        val size = 180.dp
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(Res.drawable.img_spendoo_robot),
                contentDescription = null,
                modifier = Modifier.size(size)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = size/2 + 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.splash_tagline),
                    style = Theme.typography.label.medium.large,
                    color = Theme.colorScheme.text.bodyBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                DotsProgressIndicator(
                    modifier = Modifier.padding(top = 8.dp),
                    dotSize = 7.dp,
                    spaceBetween = 4.dp
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun SplashScreenPreview() = SpendooTheme {
    SplashScreenContent()
}
