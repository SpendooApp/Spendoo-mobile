package com.spendoo.identity.presentation.screen.signup

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.entity.Gender
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.female
import spendoo.designsystem.generated.resources.ic_female
import spendoo.designsystem.generated.resources.ic_male
import spendoo.designsystem.generated.resources.male

data class SignUpUiState(
    val actionButtonState: AppButtonState = AppButtonState.Enabled,
    val fullName: String = "",
    val fullNameError: UiText? = null,
    val dateOfBirth: LocalDate? = null,
    val dateOfBirthError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isPasswordVisible: Boolean = true,
    val selectedGender: Gender? = null,
    val genderError: UiText? = null,
    val isTermsAndConditionsBottomSheetVisible: Boolean = false,
    val isPrivacyPolicyBottomSheetVisible: Boolean = false,
    val showDatePicker: Boolean = false,
)

fun Gender.toResString() = when (this) {
    Gender.MALE -> Res.string.male
    Gender.FEMALE -> Res.string.female
}

fun Gender.toResIcon() = when (this) {
    Gender.MALE -> Res.drawable.ic_male
    Gender.FEMALE -> Res.drawable.ic_female
}