package com.spendoo.identity.presentation.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.util.extentions.asString
import com.spendoo.designsystem.util.extentions.painter
import com.spendoo.designsystem.utils.asString
import com.spendoo.identity.presentation.shared.components.ScreenTemplate
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.dont_have_an_account
import spendoo.designsystem.generated.resources.enter_your_email
import spendoo.designsystem.generated.resources.enter_your_password
import spendoo.designsystem.generated.resources.forget_the_password
import spendoo.designsystem.generated.resources.hello_welcome_back
import spendoo.designsystem.generated.resources.ic_eye_closed
import spendoo.designsystem.generated.resources.ic_eye_opened
import spendoo.designsystem.generated.resources.login
import spendoo.designsystem.generated.resources.signup

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreenContent(
        state = state,
        interActionListener = viewModel
    )
}

@Composable
fun LoginScreenContent(
    state: LoginScreenState,
    interActionListener: LoginInteractionListener
) {
    ScreenTemplate(
        upperContent = {
            Text(
                text = Res.string.hello_welcome_back.asString(),
                color = Theme.colorScheme.text.headingBlue,
                style = Theme.typography.heading.large,
                textAlign = TextAlign.Center
            )
        },
        actioButtonState = state.actionButtonState,
        onClickActionButton = interActionListener::onLoginClicked,
        actionButtonText = Res.string.login.asString(),
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        underActionButtonContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Res.string.dont_have_an_account.asString(),
                    color = Theme.colorScheme.text.link,
                    style = Theme.typography.label.medium.medium,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = Res.string.signup.asString(),
                    modifier = Modifier.clickable(onClick = interActionListener::onSignUpClicked),
                    style = Theme.typography.label.semiBold.medium,
                    color = Theme.colorScheme.button.primary,
                )
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            item {
                CustomTextField(
                    value = state.email,
                    onValueChange = interActionListener::onEmailChange,
                    hint = Res.string.enter_your_email.asString(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp, top = 38.dp),
                    errorText = state.emailError?.asString(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }

            item {
                CustomTextField(
                    value = state.password,
                    onValueChange = interActionListener::onPasswordChange,
                    hint = Res.string.enter_your_password.asString(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    singleLine = true,
                    errorText = state.passwordError?.asString(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (state.isPasswordVisible) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    trailingIcon = when (state.isPasswordVisible) {
                        true -> Res.drawable.ic_eye_closed.painter()
                        false -> Res.drawable.ic_eye_opened.painter()
                    },
                    trailingIconColor = Theme.colorScheme.text.label,
                    onTrailingIconClick = interActionListener::onTogglePasswordVisibility,
                )
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = Res.string.forget_the_password.asString(),
                        color = Theme.colorScheme.button.primary,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .clickable(onClick = interActionListener::onForgotPasswordClicked),
                        style = Theme.typography.label.medium.medium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() = SpendooTheme {
    LoginScreenContent(
        state = LoginScreenState(
            email = "",
            password = "",
            emailError = null,
            passwordError = null,
            isPasswordVisible = false,
            actionButtonState = AppButtonState.Enabled
        ),
        interActionListener = object : LoginInteractionListener {
            override fun onLoginClicked() {}
            override fun onSignUpClicked() {}
            override fun onForgotPasswordClicked() {}
            override fun onEmailChange(newEmail: String) {}
            override fun onPasswordChange(newPassword: String) {}
            override fun onTogglePasswordVisibility() {}
        }
    )
}