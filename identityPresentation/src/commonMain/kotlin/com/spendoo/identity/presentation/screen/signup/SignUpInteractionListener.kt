package com.spendoo.identity.presentation.screen.signup

import com.spendoo.identity.domain.entity.Gender
import kotlinx.datetime.LocalDate

interface SignUpInteractionListener {
    fun onSignUpClicked()
    fun onLoginClicked()
    fun onFullNameChange(newFullName: String)
    fun showDatePicker()
    fun onChangeDateOfBirth(newDateOfBirth: LocalDate)
    fun onDismissDatePicker()
    fun onEmailChange(newEmail: String)
    fun onPasswordChange(newPassword: String)
    fun onTogglePasswordVisibility()
    fun onGenderSelected(gender: Gender)
    fun onTermsAndConditionsClicked()
    fun onPrivacyPolicyClicked()
    fun onTermsAndConditionsBottomSheetDismissed()
    fun onPrivacyPolicyBottomSheetDismissed()
}