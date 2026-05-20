package com.spendoo.identity.domain.repository

import com.spendoo.identity.domain.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Profile
    suspend fun updateProfileImage(fileBytes: ByteArray, fileName: String): String
    suspend fun deleteProfileImage()
}

