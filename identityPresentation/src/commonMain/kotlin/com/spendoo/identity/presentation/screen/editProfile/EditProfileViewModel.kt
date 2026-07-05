package com.spendoo.identity.presentation.screen.editProfile

import androidx.lifecycle.viewModelScope
import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.model.Gender
import com.spendoo.identity.domain.repository.ProfileRepository
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.unknown_error

class EditProfileViewModel(
    private val profileRepository: ProfileRepository
) : BaseViewModel<EditProfileUiState>(EditProfileUiState()), EditProfileInteractionListener {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        tryToCall(
            block = { profileRepository.getProfile() },
            onSuccess = { profile ->
                val parsedGender =
                    runCatching { Gender.valueOf(profile.gender.uppercase()) }.getOrDefault(Gender.MALE)
                val parsedBirthDate =
                    runCatching { LocalDate.parse(profile.birthDate) }.getOrDefault(
                        LocalDate(
                            2000,
                            1,
                            1
                        )
                    )
                updateState {
                    copy(
                        fullName = profile.fullName,
                        imageUrl = profile.imageUrl,
                        email = profile.email,
                        gender = parsedGender,
                        birthDate = parsedBirthDate
                    )
                }
            },
            onError = { handleError(it) }
        )
    }

    private fun handleError(throwable: Throwable) {
        showSnackBar(
            title = UiText.StringRes(Res.string.an_error_occurred),
            message = throwable.message?.let(UiText::DynamicString)
                ?: UiText.StringRes(Res.string.unknown_error),
            isSuccess = false
        )
    }

    override fun onClickBack() {
        popBackStack()
    }

    override fun onClickEditProfile() {
        updateState { copy(isEditMode = true) }
    }

    override fun onClickCancel() {
        updateState { copy(isEditMode = false) }
        loadProfile()
    }

    override fun onClickSave() {
        tryToCall(
            block = {
                profileRepository.updateProfile(
                    fullName = state.value.fullName,
                    gender = state.value.gender,
                    birthDate = state.value.birthDate
                )
            },
            onStart = { updateState { copy(isLoading = true) } },
            onSuccess = {
                updateState { copy(isEditMode = false) }
            },
            onError = { handleError(it) },
            onEnd = { updateState { copy(isLoading = false) } }
        )
    }

    override fun onFullNameChanged(name: String) {
        updateState { copy(fullName = name) }
    }

    override fun onBirthDateSelected(date: LocalDate) {
        updateState { copy(birthDate = date, isDatePickerVisible = false) }
    }

    override fun onGenderSelected(gender: Gender) {
        updateState { copy(gender = gender, isGenderDropdownVisible = false) }
    }

    override fun onClickChangePhoto() {
        viewModelScope.launch {
            val photo = FileKit.openFilePicker(
                type = FileKitType.Image,
                mode = FileKitMode.Single
            )
            val bytes = photo?.readBytes()
            onPhotoSelected(bytes)
        }
    }

    override fun onPhotoSelected(byteArray: ByteArray?) {
        if (byteArray != null) {
            tryToCall(
                block = { profileRepository.updateProfileImage(byteArray, "profile.jpg") },
                onStart = { updateState { copy(isLoading = true) } },
                onSuccess = { url ->
                    updateState { copy(imageUrl = url) }
                },
                onError = { handleError(it) },
                onEnd = { updateState { copy(isLoading = false) } }
            )
        }
    }

    override fun onDismissDatePicker() {
        updateState { copy(isDatePickerVisible = false) }
    }

    override fun onShowDatePicker() {
        if (state.value.isEditMode) {
            updateState { copy(isDatePickerVisible = true) }
        }
    }

    override fun onToggleGenderDropdown() {
        if (state.value.isEditMode) {
            updateState { copy(isGenderDropdownVisible = !isGenderDropdownVisible) }
        }
    }
}
