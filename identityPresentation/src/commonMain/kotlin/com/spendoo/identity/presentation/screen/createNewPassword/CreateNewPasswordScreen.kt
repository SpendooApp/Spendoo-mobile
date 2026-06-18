package com.spendoo.identity.presentation.screen.createNewPassword

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.designsystem.utils.asString
import com.spendoo.identity.presentation.shared.components.ScreenTemplate
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.create_new_password
import spendoo.designsystem.generated.resources.enter_your_new_password
import spendoo.designsystem.generated.resources.ic_eye_closed
import spendoo.designsystem.generated.resources.ic_eye_opened
import spendoo.designsystem.generated.resources.reset_password

@Composable
fun CreateNewPasswordScreen(
    email: String,
    otp: String,
    viewModel: CreateNewPasswordViewModel = koinViewModel(parameters = { parametersOf(email, otp) }),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CreateNewPasswordScreenContent(
        state = state,
        interactionListener = viewModel,
    )
}

@Composable
fun CreateNewPasswordScreenContent(
    state: CreateNewPasswordUiState,
    interactionListener: CreateNewPasswordInteractionListener,
) {
    ScreenTemplate(
        upperContent = {
            Text(
                text = Res.string.create_new_password.asString(),
                color = Theme.colorScheme.text.headingBlue,
                style = Theme.typography.heading.large,
            )
        },
        actionButtonState = state.actionButtonState,
        onClickActionButton = interactionListener::onResetPasswordClicked,
        actionButtonText = Res.string.reset_password.asString(),
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                CustomTextField(
                    value = state.password,
                    onValueChange = interactionListener::onPasswordChange,
                    hint = Res.string.enter_your_new_password.asString(),
                    errorText = state.passwordError?.asString(),
                    singleLine = true,
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
                    onTrailingIconClick = interactionListener::onTogglePasswordVisibility,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, top = 38.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun CreateNewPasswordScreenPreview() = SpendooTheme {
    CreateNewPasswordScreenContent(
        state = CreateNewPasswordUiState(),
        interactionListener = object : CreateNewPasswordInteractionListener {
            override fun onPasswordChange(newPassword: String) {}
            override fun onTogglePasswordVisibility() {}
            override fun onResetPasswordClicked() {}
        }
    )
}


