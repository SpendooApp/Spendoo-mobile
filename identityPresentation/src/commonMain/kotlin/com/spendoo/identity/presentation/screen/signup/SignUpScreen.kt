package com.spendoo.identity.presentation.screen.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.components.text.MultiHighlightedClickableText
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.text.TextSegment
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.format
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.asString
import com.spendoo.identity.domain.entity.Gender
import com.spendoo.identity.presentation.screen.signup.components.PrivacyPolicyBottomSheet
import com.spendoo.identity.presentation.screen.signup.components.SelectableGenderButton
import com.spendoo.identity.presentation.screen.signup.components.TermsAndConditionsBottomSheet
import com.spendoo.identity.presentation.shared.components.ScreenTemplate
import kotlinx.datetime.LocalDate
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.already_have_an_account
import spendoo.designsystem.generated.resources.and
import spendoo.designsystem.generated.resources.by_continuing_you_agree_to_our
import spendoo.designsystem.generated.resources.create_account
import spendoo.designsystem.generated.resources.enter_your_birth_date
import spendoo.designsystem.generated.resources.enter_your_email
import spendoo.designsystem.generated.resources.enter_your_full_name
import spendoo.designsystem.generated.resources.enter_your_password
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.ic_eye_closed
import spendoo.designsystem.generated.resources.ic_eye_opened
import spendoo.designsystem.generated.resources.login
import spendoo.designsystem.generated.resources.please_select_your_gender
import spendoo.designsystem.generated.resources.privacy_policy
import spendoo.designsystem.generated.resources.signup
import spendoo.designsystem.generated.resources.terms_and_conditions
import spendoo.designsystem.generated.resources.what_is_your_gender

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SignUpScreenContent(
        interactionListener = viewModel,
        state = state
    )
}

@Composable
private fun SignUpScreenContent(
    interactionListener: SignUpInteractionListener,
    state: SignUpUiState
) {
    val focusManager = LocalFocusManager.current

    ScreenTemplate(
        upperContent = {
            Text(
                text = Res.string.create_account.asString(),
                color = Theme.colorScheme.text.headingBlue,
                style = Theme.typography.heading.large
            )
        },
        actionButtonState = state.actionButtonState,
        onClickActionButton = interactionListener::onSignUpClicked,
        actionButtonText = Res.string.signup.asString(),
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        underActionButtonContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Res.string.already_have_an_account.asString(),
                    color = Theme.colorScheme.text.link,
                    style = Theme.typography.label.medium.medium,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = Res.string.login.asString(),
                    modifier = Modifier.clickable(onClick = interactionListener::onLoginClicked),
                    style = Theme.typography.label.semiBold.medium,
                    color = Theme.colorScheme.button.primary,
                )
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f, fill = false)) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 38.dp, bottom = 20.dp)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = Res.string.what_is_your_gender.asString(),
                        color = Theme.colorScheme.text.title,
                        style = Theme.typography.label.medium.medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        SelectableGenderButton(
                            gender = Gender.MALE,
                            isSelected = state.selectedGender == Gender.MALE,
                            onClick = { interactionListener.onGenderSelected(Gender.MALE) },
                        )
                        SelectableGenderButton(
                            gender = Gender.FEMALE,
                            isSelected = state.selectedGender == Gender.FEMALE,
                            onClick = { interactionListener.onGenderSelected(Gender.FEMALE) },
                        )
                    }
                    AnimatedVisibility(
                        visible = state.genderError.asString().isNotBlank(),
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Text(
                            text = state.genderError.asString(),
                            color = Theme.colorScheme.additional.onError,
                            modifier = Modifier.padding(top = 8.dp),
                            style = Theme.typography.body.small,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
            item {
                CustomTextField(
                    value = state.fullName,
                    onValueChange = interactionListener::onFullNameChange,
                    hint = Res.string.enter_your_full_name.asString(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    errorText = state.fullNameError?.asString(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }
            item {
                CustomTextField(
                    value = state.dateOfBirth.format(),
                    onValueChange = { },
                    hint = Res.string.enter_your_birth_date.asString(),
                    trailingIcon = Res.drawable.ic_date.painter(),
                    trailingIconColor = Theme.colorScheme.text.label,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    errorText = state.dateOfBirthError?.asString(),
                    enabled = false,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                        .clickableNoRipple {
                            focusManager.clearFocus()
                            interactionListener.showDatePicker()
                        }
                )
            }
            item {
                CustomTextField(
                    value = state.email,
                    onValueChange = interactionListener::onEmailChange,
                    hint = Res.string.enter_your_email.asString(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    errorText = state.emailError?.asString(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
            }
            item {
                CustomTextField(
                    value = state.password,
                    onValueChange = interactionListener::onPasswordChange,
                    hint = Res.string.enter_your_password.asString(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
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
                )
            }
            item {
                MultiHighlightedClickableText(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    segments = listOf(
                        TextSegment.Normal(Res.string.by_continuing_you_agree_to_our.asString()),
                        TextSegment.Highlighted(
                            Res.string.terms_and_conditions.asString(),
                            onClick = interactionListener::onTermsAndConditionsClicked
                        ),
                        TextSegment.Normal(Res.string.and.asString()),
                        TextSegment.Highlighted(
                            Res.string.privacy_policy.asString(),
                            onClick = interactionListener::onPrivacyPolicyClicked
                        )
                    )
                )
            }
        }
        TermsAndConditionsBottomSheet(
            isVisible = state.isTermsAndConditionsBottomSheetVisible,
            onDismissRequest = interactionListener::onTermsAndConditionsBottomSheetDismissed
        )
        PrivacyPolicyBottomSheet(
            isVisible = state.isPrivacyPolicyBottomSheetVisible,
            onDismissRequest = interactionListener::onPrivacyPolicyBottomSheetDismissed
        )
        DatePicker(
            showDialog = state.showDatePicker,
            selectedDate = state.dateOfBirth,
            maxDate = state.maxAllowedDate,
            onDateSelected = {
                interactionListener.onChangeDateOfBirth(it)
            },
            onDismiss = { interactionListener.onDismissDatePicker() }
        )
    }
}


@Composable
@Preview
fun SignUpScreenPreview() = SpendooTheme {
    SignUpScreenContent(
        interactionListener = object : SignUpInteractionListener {
            override fun onSignUpClicked() {}
            override fun onLoginClicked() {}
            override fun onFullNameChange(newFullName: String) {}
            override fun showDatePicker() {}
            override fun onChangeDateOfBirth(newDateOfBirth: LocalDate) {}
            override fun onDismissDatePicker() {}
            override fun onEmailChange(newEmail: String) {}
            override fun onPasswordChange(newPassword: String) {}
            override fun onTogglePasswordVisibility() {}
            override fun onGenderSelected(gender: Gender) {}
            override fun onTermsAndConditionsClicked() {}
            override fun onPrivacyPolicyClicked() {}
            override fun onTermsAndConditionsBottomSheetDismissed() {}
            override fun onPrivacyPolicyBottomSheetDismissed() {}
        },
        state = SignUpUiState(
            genderError = UiText.StringRes(Res.string.please_select_your_gender),
        )
    )
}

