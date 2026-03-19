package com.spendoo.identity.presentation.screen.signup

import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.entity.Gender
import com.spendoo.identity.domain.model.RegisterRequest
import com.spendoo.identity.domain.repository.RegisterRepository
import com.spendoo.identity.domain.useCase.validation.auth.ValidationUseCase
import com.spendoo.identity.presentation.navigation.VerifyEmailRoute
import com.spendoo.identity.presentation.shared.BaseViewModel
import kotlinx.datetime.LocalDate

class SignUpViewModel(
    private val registerRepository: RegisterRepository,
    private val validationUseCase: ValidationUseCase,
) : BaseViewModel<SignUpUiState>(SignUpUiState()), SignUpInteractionListener {

    init {
        updateState {
            copy(
                maxAllowedDate = validationUseCase.getMaximumAllowedRegistrationDate()
            )
        }
    }

    override fun onFullNameChange(newFullName: String) {
        updateState { copy(fullName = newFullName) }
        validateFullName()
    }

    override fun showDatePicker() {
        updateState { copy(showDatePicker = true) }
    }

    override fun onChangeDateOfBirth(newDateOfBirth: LocalDate) {
        updateState { copy(dateOfBirth = newDateOfBirth) }
        validateAge()
    }

    override fun onDismissDatePicker() {
        updateState { copy(showDatePicker = false) }
    }

    override fun onEmailChange(newEmail: String) {
        updateState { copy(email = newEmail) }
        validateEmail()
    }

    override fun onPasswordChange(newPassword: String) {
        updateState { copy(password = newPassword) }
        validatePassword()
    }

    override fun onTogglePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    override fun onGenderSelected(gender: Gender) {
        updateState { copy(selectedGender = gender, genderError = null) }
    }

    override fun onTermsAndConditionsClicked() {
        updateState { copy(isTermsAndConditionsBottomSheetVisible = true) }
    }

    override fun onPrivacyPolicyClicked() {
        updateState { copy(isPrivacyPolicyBottomSheetVisible = true) }
    }

    override fun onTermsAndConditionsBottomSheetDismissed() {
        updateState { copy(isTermsAndConditionsBottomSheetVisible = false) }
    }

    override fun onPrivacyPolicyBottomSheetDismissed() {
        updateState { copy(isPrivacyPolicyBottomSheetVisible = false) }
    }


    override fun onSignUpClicked() {
        validateFields()

        val selectedGender = state.value.selectedGender ?: let {
            updateState { copy(genderError = UiText.DynamicString("Please select your gender")) }
            return
        }

        if (state.value.fullNameError != null || state.value.dateOfBirthError != null || state.value.emailError != null || state.value.passwordError != null) {
            return
        }


        val dateOfBirth = state.value.dateOfBirth ?: return

        tryToCall(
            onStart = {
                updateState { copy(actionButtonState = AppButtonState.Loading) }
            },
            block = {
                registerRepository.register(
                    RegisterRequest(
                        email = state.value.email,
                        fullName = state.value.fullName,
                        birthDate = dateOfBirth,
                        gender = selectedGender,
                        password = state.value.password,
                    )
                )
            },
            onSuccess = {
                navigate(VerifyEmailRoute(email = state.value.email, isForgetPasswordFlow = false))
            },
            onError = {
                println("Error: $it")
            },
            onEnd = {
                updateState { copy(actionButtonState = AppButtonState.Enabled) }
            }
        )
    }

    override fun onLoginClicked() {
        popBackStack()
    }

    private fun validateFields() {
        validateFullName()
        validateAge()
        validateEmail()
        validatePassword()
    }

    private fun validateFullName() {
        val isValid = validationUseCase.validateFullName(state.value.fullName)
        updateState {
            copy(
                fullNameError = if (isValid) {
                    null
                } else {
                    UiText.DynamicString("Please enter your full name")
                }
            )
        }
    }

    private fun validateAge() {
        val isValid = state.value.dateOfBirth?.let { dateOfBirth ->
            validationUseCase.validateAge(dateOfBirth)
        } ?: false
        updateState {
            copy(
                dateOfBirthError = if (isValid) {
                    null
                } else {
                    UiText.DynamicString("You must be at least 8 years old")
                }
            )
        }
    }

    private fun validateEmail() {
        val isValid = validationUseCase.validateEmail(state.value.email)
        updateState {
            copy(
                emailError = if (isValid) {
                    null
                } else {
                    UiText.DynamicString("Invalid email")
                }
            )
        }
    }

    private fun validatePassword() {
        val isValid = validationUseCase.validatePassword(state.value.password)
        updateState {
            copy(
                passwordError = if (isValid) {
                    null
                } else {
                    UiText.DynamicString("Invalid password")
                }
            )
        }
    }
}