package com.spendoo.identity.presentation.screen.editProfile

import com.spendoo.identity.domain.model.Gender
import kotlinx.datetime.LocalDate

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val fullName: String = "",
    val birthDate: LocalDate = LocalDate(2003, 4, 19),
    val email: String = "",
    val gender: Gender = Gender.MALE,
    val imageUrl: String? = null,
    val isDatePickerVisible: Boolean = false,
    val isGenderDropdownVisible: Boolean = false,
)
