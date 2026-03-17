package com.spendoo.identity.presentation.screen.verifyEmail

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.OtpInputField
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.util.formatTime
import com.spendoo.designsystem.utils.asString
import com.spendoo.identity.presentation.shared.components.ScreenTemplate
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun VerifyEmailScreen(
    viewModel: VerifyEmailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    VerifyEmailScreenContent(
        state = state,
        interactionListener = viewModel,
    )
}

@Composable
private fun VerifyEmailScreenContent(
    state: VerifyEmailUiState,
    interactionListener: VerifyEmailInteractionListener,
) {
    ScreenTemplate(
        upperContent = {
            Text(
                text = "Verify your Email",
                color = Theme.colorScheme.text.headingBlue,
                style = Theme.typography.heading.large,
            )
        },
        actioButtonState = state.actionButtonState,
        onClickActionButton = interactionListener::onVerifyClicked,
        actionButtonText = "Verify Code",
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        underActionButtonContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val animatedTextColor by animateColorAsState(
                    targetValue = if (state.canResend && state.actionButtonState != AppButtonState.Loading) Theme.colorScheme.button.primary
                    else Theme.colorScheme.text.label
                )
                Text(
                    text = "Didn't receive code?",
                    color = Theme.colorScheme.text.link,
                    style = Theme.typography.label.medium.medium,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = "Resend",
                    color = animatedTextColor,
                    style = Theme.typography.label.semiBold.medium,
                    modifier = Modifier.clickable(
                        onClick = interactionListener::onResendClicked,
                        enabled = state.canResend && state.actionButtonState != AppButtonState.Loading
                    )
                )
            }
        },
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Text(
                    text = "Enter code sent on your email",
                    color = Theme.colorScheme.text.label,
                    style = Theme.typography.label.medium.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, top = 38.dp)
                )
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OtpInputField(
                        otpText = state.otp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp, start = 8.dp, end = 8.dp),
                        otpLength = 5,
                        errorText = state.otpError?.asString(),
                        spaceBetweenCharacters = 12.dp,
                        onOtpModified = interactionListener::onOtpChange,
                    )
                    Text(
                        text = formatTime(state.timeRemaining),
                        style = Theme.typography.body.medium.copy(
                            color = Theme.colorScheme.brand.primary
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun VerifyEmailScreenPreview() = SpendooTheme {
    VerifyEmailScreenContent(
        state = VerifyEmailUiState(
            canResend = true,
        ),
        interactionListener = object : VerifyEmailInteractionListener {
            override fun onOtpChange(newOtp: String) {}
            override fun onVerifyClicked() {}
            override fun onResendClicked() {}
        }
    )
}
