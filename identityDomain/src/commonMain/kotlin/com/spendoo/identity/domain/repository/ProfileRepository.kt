package com.spendoo.identity.domain.repository

import com.spendoo.identity.domain.model.Gender
import com.spendoo.identity.domain.model.Profile
import kotlinx.datetime.LocalDate

interface ProfileRepository {
    suspend fun getProfile(): Profile
    suspend fun updateProfileImage(fileBytes: ByteArray, fileName: String): String
    suspend fun deleteProfileImage()
    suspend fun updateProfile(fullName: String, gender: Gender, birthDate: LocalDate)
}


