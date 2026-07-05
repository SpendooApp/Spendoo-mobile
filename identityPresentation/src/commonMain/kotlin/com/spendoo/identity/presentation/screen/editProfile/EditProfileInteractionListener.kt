package com.spendoo.identity.presentation.screen.editProfile

import com.spendoo.identity.domain.model.Gender
import kotlinx.datetime.LocalDate

interface EditProfileInteractionListener {
    fun onClickBack()
    fun onClickEditProfile()
    fun onClickCancel()
    fun onClickSave()
    fun onFullNameChanged(name: String)
    fun onBirthDateSelected(date: LocalDate)
    fun onGenderSelected(gender: Gender)
    fun onClickChangePhoto()
    fun onPhotoSelected(byteArray: ByteArray?)
    fun onDismissDatePicker()
    fun onShowDatePicker()
    fun onToggleGenderDropdown()
}
