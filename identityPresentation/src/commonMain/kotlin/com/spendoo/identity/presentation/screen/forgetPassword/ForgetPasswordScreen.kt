package com.spendoo.identity.presentation.screen.forgetPassword

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.util.extentions.asString
import com.spendoo.designsystem.utils.asString
import com.spendoo.identity.presentation.shared.components.ScreenTemplate
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.enter_your_email

@Composable
fun ForgetPasswordScreen(viewModel: ForgetPasswordViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ForgetPasswordScreenContent(
        state = state,
        interactionListener = viewModel
    )
}

@Composable
fun ForgetPasswordScreenContent(
    state: ForgetPasswordUiState,
    interactionListener: ForgetPasswordInteractionListener,
) {
    ScreenTemplate(
        upperContent = {
            Text(
                text = "Forget Password ?",
                color = Theme.colorScheme.text.headingBlue,
                style = Theme.typography.heading.large,
            )
        },
        actioButtonState = state.actionButtonState,
        onClickActionButton = interactionListener::onSendCodeClicked,
        actionButtonText = "Send Code",
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                CustomTextField(
                    value = state.email,
                    onValueChange = interactionListener::onEmailChange,
                    hint = Res.string.enter_your_email.asString(),
                    errorText = state.emailError?.asString(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp, top = 38.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun ForgetPasswordScreenPreview() = SpendooTheme {
    ForgetPasswordScreenContent(
        state = ForgetPasswordUiState(),
        interactionListener = object : ForgetPasswordInteractionListener {
            override fun onEmailChange(newEmail: String) {}
            override fun onSendCodeClicked() {}
        }
    )
}
