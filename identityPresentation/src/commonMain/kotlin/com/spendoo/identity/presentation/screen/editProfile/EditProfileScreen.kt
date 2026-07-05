package com.spendoo.identity.presentation.screen.editProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.components.general.AppSegmentedControl
import com.spendoo.designsystem.components.icon.ProfileAddPhoto
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.identity.domain.model.Gender
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.birth_date
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.edit_profile
import spendoo.designsystem.generated.resources.email
import spendoo.designsystem.generated.resources.female
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.male
import spendoo.designsystem.generated.resources.save
import spendoo.designsystem.generated.resources.username

fun Gender.getName(): StringResource = when (this) {
    Gender.MALE -> Res.string.male
    Gender.FEMALE -> Res.string.female
}

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditProfileContent(
        state = state,
        listener = viewModel
    )
}

@Composable
fun EditProfileContent(
    state: EditProfileUiState,
    listener: EditProfileInteractionListener
) {
    DatePicker(
        showDialog = state.isDatePickerVisible,
        selectedDate = state.birthDate,
        onDateSelected = listener::onBirthDateSelected,
        onDismiss = listener::onDismissDatePicker
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        TopBar(
            title = "",
            onBackClicked = listener::onClickBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileAddPhoto(
                imageUrl = state.imageUrl.orEmpty(),
                modifier = Modifier.clickableNoRipple {
                    listener.onClickChangePhoto()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = state.fullName,
                style = Theme.typography.heading.large,
                color = Theme.colorScheme.text.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomTextField(
                    value = state.email,
                    onValueChange = { },
                    hint = stringResource(Res.string.email),
                    readOnly = true,
                    enabled = false
                )

                CustomTextField(
                    value = state.fullName,
                    onValueChange = listener::onFullNameChanged,
                    hint = stringResource(Res.string.username),
                    enabled = state.isEditMode,
                    readOnly = !state.isEditMode
                )

                CustomTextField(
                    modifier = Modifier.clickableNoRipple{ listener.onShowDatePicker() },
                    value = state.birthDate.toString(),
                    onValueChange = { },
                    hint = stringResource(Res.string.birth_date),
                    readOnly = true,
                    enabled = false,
                    trailingIcon = Res.drawable.ic_date.painter(),
                    onTrailingIconClick = { listener.onShowDatePicker() }
                )

                AppSegmentedControl(
                    options = listOf(Gender.MALE, Gender.FEMALE),
                    selectedOption = state.gender,
                    onOptionSelected = { if (state.isEditMode) listener.onGenderSelected(it) },
                    getName = { getName() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            if (!state.isEditMode) {
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.edit_profile),
                    type = AppButtonType.Primary,
                    onClick = { listener.onClickEditProfile() },
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(Res.string.cancel),
                        type = AppButtonType.Secondary,
                        onClick = { listener.onClickCancel() },
                    )
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(Res.string.save),
                        type = AppButtonType.Primary,
                        onClick = { listener.onClickSave() },
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    SpendooTheme {
        EditProfileContent(
            state = EditProfileUiState(
                fullName = "Ali Ahmed",
                email = "Ali_Ahmed@gmail.com",
                isEditMode = false
            ),
            listener = object : EditProfileInteractionListener {
                override fun onClickBack() {}
                override fun onClickEditProfile() {}
                override fun onClickCancel() {}
                override fun onClickSave() {}
                override fun onFullNameChanged(name: String) {}
                override fun onBirthDateSelected(date: LocalDate) {}
                override fun onGenderSelected(gender: Gender) {}
                override fun onClickChangePhoto() {}
                override fun onPhotoSelected(byteArray: ByteArray?) {}
                override fun onDismissDatePicker() {}
                override fun onShowDatePicker() {}
                override fun onToggleGenderDropdown() {}
            }
        )
    }
}
